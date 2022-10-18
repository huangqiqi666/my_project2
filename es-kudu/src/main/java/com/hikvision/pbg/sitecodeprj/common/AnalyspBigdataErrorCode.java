package com.hikvision.pbg.sitecodeprj.common;

import lombok.Getter;

/*
 * @Description analysp bigdata errorcode
 * @Date 17:42 2022/9/26
 * @Author xiaokai
 **/
@Getter
public enum AnalyspBigdataErrorCode {
    ERR_AUTHENTICATION_COMMON("0x123500001", "General Authenticationerror"),
    ERR_AUTHENTICATION_NOTLOGIN("0x123500002", "User is not logged in"),
    ERR_AUTHENTICATION_TOKEN("0x123500003", "Token authentication error"),
    ERR_AUTHENTICATION_TOKEN_OVERDUE("0x123500004", "Token has expired"),
    ERR_AUTHENTICATION_TGT("0x123500005", "Failed to get user TGT"),

    ERR_NETWORK_COMMON("0x123500100", "General network error"),
    ERR_NETWORK_TIMEOUT("0x123500101", "Network access timeout"),
    ERR_NETWORK_ADDRESS("0x123500102", "Network address error"),
    ERR_NETWORK_WEBSOCKET_TIMEOUR("0x123500103", "websocket connection exception"),
    ERR_NETWORK_AMQ_TIMEOUR("0x123500104", "amq connection exception"),
    ERR_NETWORK_RMQ_TIMEOUR("0x123500105", "rmq connection error"),
    ERR_NETWORK_REDIS_TIMEOUR("0x123500106", "redis connection error"),

    ERR_DB_COMMON("0x123500200", "General database error"),
    ERR_DB_UPDATE("0x123500201", "Update data failed"),
    ERR_DB_QUERY("0x123500202", "Querying data failed"),
    ERR_DB_INSERT("0x123500203", "Failed to insert data"),
    ERR_DB_DELETE("0x123500204", "Failed to delete data"),
    ERR_DB_DATA_NOT_EXIST("0x123500205", "Data does not exist"),
    ERR_DB_INSERT_HUMAN_EXISTED("0x123500206", "Human existed"),

    ERR_SYSTEM_COMMON("0x123500300", "General system error"),
    ERR_SYSTEM_TYPE_TRANSFROM("0x123500301", "Type conversion error"),

    ERR_DEVICE_COMMON("0x123500400", "General device error"),

    ERR_PARAM_COMMON("0x123502000", "General parameter error"),
    ERR_PARAM_ISNULL("0x123502001", "Request parameter is empty"),

    ERR_SERVICE_COMMON("0x123502100", "General service error"),
    ERR_SERVICE_INTERFACE_CALL_FAILED("0x123502101", "interface call failed"),
    ERR_SERVICE_EVALUATE_TASK_NAME_REPEAT("0x123502102", "evaluate task name repeat"),
    ERR_SERVICE_WEBSOCKET_SEND_MESSAGE("0x123502103", "send websocket message failed"),
    ERR_SERVICE_AMQ_HANDLE("0x123502104", "handle amq message failed"),
    ERR_SERVICE_RMQ_HANDLE("0x123502105", "handle rmq message failed"),
    ERR_SERVICE_NORELATIONVIOLATOR_ERROR("0x123502107", "no relation violator"),
    ERR_SERVICE_LOADFILE_ERROR("0x123502108", "load file  exception"),
    ERR_SERVICE_FINDADDR_ERROR("0x123502109", "find hgis addr  exception"),
    ERR_SERVICE_CLOSE_STREAM("0x12350210a", "close stream error"),
    ERR_SERVICE_NOT_CONFIG_STORAGE("0x12350210b", "not config storage"),
    ERR_SERVICE_NOT_QUERY_MEDIAFILE("0x12350210c", "not query media file"),
    ERR_SERVICE_DICT_NAME_REPEAT("0x12350210d", "dictionary name repeat"),
    ERR_SERVICE_DICT_DATAKEY_REPEAT("0x12350210e", "dictionary dataKey repeat"),


    ERR_EVENT_INFO_SAVE_ERROR("0x123502110", "Database error, save event info fail"),
    ERR_EVENT_STATUS_UPDATE_ERROR("0x123502111", "Database error, update event status fail"),
    ERR_EVENT_LIST_QUERY_ERROR("0x123502112", "Database error, query event list fail"),
    ERR_EVENT_LIST_COUNT_ERROR("0x123502113", "Database error, count the event number fail"),
    ERR_EVENT_DETAIL_QUERY_ERROR("0x123502114", "Database error, query event detail fail"),
    ERR_EVENT_SAVE_RELATION_FAILS_ERROR("0x123502115", "Database error, save event relation files fail"),
    ERR_EVENT_RELATION_FAILS_QUERY_ERROR("0x123502116", "Database error, query event relation files fail"),
    ERR_EVENT_HANDLER_QUERY_FAIL("0x123502117", "The event handler query fail"),
    ERR_EVENT_STATUS_NOT_MATCH("0x123502118", "The event status doesn't match with current operation."),
    ERR_EVENT_HANDLER_NOT_POINTED("0x123502119", "The event handler doesn't pointed."),

    ERR_COMMON_REMOTECALL_GETPREVIEWURL_ERROR("0x123502144", "get vnsc preview url fail."),
    ERR_COMMON_REMOTECALL_GETPLACKBACKURL_ERROR("0x123502145", "get vnsc playback url fail."),
    ERR_COMMON_REMOTECALL_QUERYCAMERA_ERROR("0x123502146", "query camera info fail."),
    ERR_CASCADE_RABBITMQ_SEND_ERROR("0x123502147", "send rabbitmq info fail."),
    ERR_CASCADE_HANDLE_LOWER_PLATFORM_DATA_ERROR("0x123502148", "handle lower platform cascade data fail."),
    ERR_CASCADE_HANDLE_UPPER_PLATFORM_RESULT_ERROR("0x123502149", "handle upper platform result info fail."),
    ERR_CASCADE_FULL_TASK_ERROR("0x12350214a", "full cascade data fail."),


    ;

    private final String code;
    private final String msg;

    AnalyspBigdataErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return msg;
    }

}
