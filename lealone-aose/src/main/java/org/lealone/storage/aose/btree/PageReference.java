/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.storage.aose.btree;

import java.nio.ByteBuffer;
import java.util.List;

import org.lealone.db.IDatabase;
import org.lealone.db.async.Future;
import org.lealone.db.session.Session;
import org.lealone.net.NetNode;
import org.lealone.storage.PageKey;
import org.lealone.storage.StorageCommand;

public class PageReference {

    private static final long REMOTE_PAGE_POS = -1;

    static PageReference createRemotePageReference(Object key, boolean first) {
        return new PageReference(null, REMOTE_PAGE_POS, key, first);
    }

    static PageReference createRemotePageReference() {
        return new PageReference(null, REMOTE_PAGE_POS);
    }

    volatile BTreePage page;
    PageKey pageKey;
    long pos;
    List<String> replicationHostIds;

    public PageReference(long pos) {
        this.pos = pos;
    }

    public PageReference(BTreePage page, long pos) {
        this.page = page;
        this.pos = pos;
        if (page != null) {
            replicationHostIds = page.getReplicationHostIds();
        }
    }

    public PageReference(BTreePage page) {
        this.page = page;
        if (page != null) {
            pos = page.getPos();
            replicationHostIds = page.getReplicationHostIds();
        }
    }

    PageReference(BTreePage page, long pos, Object key, boolean first) {
        this(page, pos);
        setPageKey(key, first);
    }

    PageReference(BTreePage page, Object key, boolean first) {
        this(page);
        setPageKey(key, first);
    }

    public void replacePage(BTreePage page) {
        this.page = page;
        if (page != null) {
            pos = page.getPos();
            replicationHostIds = page.getReplicationHostIds();
        }
    }

    @Override
    public String toString() {
        return "PageReference[ pos=" + pos + "]";
    }

    void setPageKey(Object key, boolean first) {
        pageKey = new PageKey(key, first);
    }

    boolean isRemotePage() {
        if (page != null)
            return page.isRemote();
        else
            return pos == REMOTE_PAGE_POS;
    }

    boolean isLeafPage() {
        if (page != null)
            return page.isLeaf();
        else
            return pos != REMOTE_PAGE_POS && PageUtils.isLeafPage(pos);
    }

    boolean isNodePage() {
        if (page != null)
            return page.isNode();
        else
            return pos != REMOTE_PAGE_POS && PageUtils.isNodePage(pos);
    }

    synchronized BTreePage readRemotePage(BTreeMap<Object, Object> map) {
        if (page != null) {
            return page;
        }
        IDatabase db = map.getDatabase();
        // TODO 支持多节点容错
        String remoteHostId = replicationHostIds.get(0);
        List<NetNode> replicationNodes = BTreeMap.getReplicationNodes(db, new String[] { remoteHostId });
        Session s = db.createSession(replicationNodes);
        try (StorageCommand c = s.createStorageCommand()) {
            Future<ByteBuffer> f = c.readRemotePage(map.getName(), pageKey);
            ByteBuffer pageBuffer = f.get();
            page = BTreePage.readReplicatedPage(map, pageBuffer);
        }

        if (!map.isShardingMode() || (page.getReplicationHostIds() != null
                && page.getReplicationHostIds().contains(NetNode.getLocalTcpHostAndPort()))) {
            pos = 0;
        }
        return page;
    }

    // test only
    public BTreePage getPage() {
        return page;
    }

    // test only
    public void setReplicationHostIds(List<String> replicationHostIds) {
        this.replicationHostIds = replicationHostIds;
    }
}
