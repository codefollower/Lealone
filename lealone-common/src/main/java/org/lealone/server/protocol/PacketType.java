/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.server.protocol;

// 协议包的类型值没有使用枚举常量的ordinal值而是自定义的值，这样允许改变枚举常量的定义顺序，
// 类型值会编码到协议包中，所以不能随便改动，不同协议包的类型值之间有意设置了间隔，用于后续加新的协议包
public enum PacketType {

    SESSION_INIT(0),
    SESSION_INIT_ACK(1),
    SESSION_CANCEL_STATEMENT(2),
    SESSION_SET_AUTO_COMMIT(3),
    SESSION_CLOSE(4),

    PREPARED_STATEMENT_PREPARE(10),
    PREPARED_STATEMENT_PREPARE_ACK(11),
    PREPARED_STATEMENT_PREPARE_READ_PARAMS(12),
    PREPARED_STATEMENT_PREPARE_READ_PARAMS_ACK(13),
    PREPARED_STATEMENT_QUERY(14),
    // PREPARED_STATEMENT_QUERY_ACK(15), // ACK直接用STATEMENT_QUERY_ACK
    PREPARED_STATEMENT_UPDATE(16),
    // PREPARED_STATEMENT_UPDATE_ACK(17),
    PREPARED_STATEMENT_GET_META_DATA(18),
    PREPARED_STATEMENT_GET_META_DATA_ACK(19),
    PREPARED_STATEMENT_CLOSE(20),

    STATEMENT_QUERY(30),
    STATEMENT_QUERY_ACK(31),
    STATEMENT_UPDATE(32),
    STATEMENT_UPDATE_ACK(33),

    BATCH_STATEMENT_UPDATE(40),
    BATCH_STATEMENT_UPDATE_ACK(41),
    BATCH_STATEMENT_PREPARED_UPDATE(42), // ACK直接用BATCH_STATEMENT_UPDATE_ACK

    RESULT_FETCH_ROWS(50),
    RESULT_FETCH_ROWS_ACK(51),
    RESULT_CHANGE_ID(52),
    RESULT_RESET(53),
    RESULT_CLOSE(54),

    LOB_READ(60),
    LOB_READ_ACK(61),

    REPLICATION_UPDATE(70),
    REPLICATION_UPDATE_ACK(71),
    REPLICATION_PREPARED_UPDATE(72),
    REPLICATION_PREPARED_UPDATE_ACK(73),
    REPLICATION_CHECK_CONFLICT(74),
    REPLICATION_CHECK_CONFLICT_ACK(75),
    REPLICATION_HANDLE_CONFLICT(76),
    REPLICATION_HANDLE_REPLICA_CONFLICT(77),

    DISTRIBUTED_TRANSACTION_QUERY(90),
    DISTRIBUTED_TRANSACTION_QUERY_ACK(91),
    DISTRIBUTED_TRANSACTION_PREPARED_QUERY(92),
    DISTRIBUTED_TRANSACTION_PREPARED_QUERY_ACK(93),
    DISTRIBUTED_TRANSACTION_UPDATE(94),
    DISTRIBUTED_TRANSACTION_UPDATE_ACK(95),
    DISTRIBUTED_TRANSACTION_PREPARED_UPDATE(96),
    DISTRIBUTED_TRANSACTION_PREPARED_UPDATE_ACK(97),

    DISTRIBUTED_TRANSACTION_COMMIT(98),
    DISTRIBUTED_TRANSACTION_COMMIT_ACK(99),
    DISTRIBUTED_TRANSACTION_ROLLBACK(100),
    DISTRIBUTED_TRANSACTION_ADD_SAVEPOINT(101),
    DISTRIBUTED_TRANSACTION_ROLLBACK_SAVEPOINT(102),
    DISTRIBUTED_TRANSACTION_VALIDATE(103),
    DISTRIBUTED_TRANSACTION_VALIDATE_ACK(104),
    DISTRIBUTED_TRANSACTION_COMMIT_FINAL(105),

    DISTRIBUTED_TRANSACTION_REPLICATION_UPDATE(110),
    DISTRIBUTED_TRANSACTION_REPLICATION_UPDATE_ACK(111),
    DISTRIBUTED_TRANSACTION_REPLICATION_PREPARED_UPDATE(112),
    DISTRIBUTED_TRANSACTION_REPLICATION_PREPARED_UPDATE_ACK(113),

    STORAGE_GET(120),
    STORAGE_GET_ACK(121),
    STORAGE_PUT(122),
    STORAGE_PUT_ACK(123),
    STORAGE_APPEND(124),
    STORAGE_APPEND_ACK(125),
    STORAGE_REPLACE(126),
    STORAGE_REPLACE_ACK(127),
    STORAGE_REMOVE(128),
    STORAGE_REMOVE_ACK(129),

    STORAGE_PREPARE_MOVE_LEAF_PAGE(140),
    STORAGE_PREPARE_MOVE_LEAF_PAGE_ACK(141),
    STORAGE_MOVE_LEAF_PAGE(142),
    STORAGE_REMOVE_LEAF_PAGE(143),
    STORAGE_REPLICATE_PAGES(144),
    STORAGE_READ_PAGE(145),
    STORAGE_READ_PAGE_ACK(146),

    P2P_ECHO(150),
    P2P_GOSSIP_DIGEST_SYN(151),
    P2P_GOSSIP_DIGEST_ACK(152),
    P2P_GOSSIP_DIGEST_ACK2(153),
    P2P_GOSSIP_SHUTDOWN(154),
    P2P_REQUEST_RESPONSE(155),

    VOID(155 + 1);

    public final int value;

    private PacketType(int value) {
        this.value = value;
    }
}
