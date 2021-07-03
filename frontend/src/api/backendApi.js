const API_BASE_URL = 'http://192.168.88.2:8080/api/v1/';
// const API_BASE_URL = '/api/v1/';
export default {
    API_REGISTRATION_URL: API_BASE_URL + 'admin/registration',
    API_AUTH_URL: API_BASE_URL + 'auth/login',
    API_ADMIN_URL: API_BASE_URL + 'admin/users',
    API_ORDER_URL: API_BASE_URL + 'order/',
    API_ORDER_PAGE_URL: API_BASE_URL + 'order/?page=',
    API_USER_URL: API_BASE_URL + 'user/',

    API_DEVICE_URL: API_BASE_URL + 'device/',
    API_DEVICE_VIEW_SET_URL: API_BASE_URL + 'admin/device/view-set',
    API_DEVICE_TO_SET_URL: API_BASE_URL + 'admin/device/to-set/',
    API_DEVICE_DEL_SET_URL: API_BASE_URL + 'admin/device/del-set/'
    ,
    API_MATERIAL_URL: API_BASE_URL + 'material/',
    API_MATERIAL_VIEW_SET_URL: API_BASE_URL + 'admin/material/view-set',
    API_MATERIAL_TO_SET_URL: API_BASE_URL + 'admin/material/to-set/',
    API_MATERIAL_DEL_SET_URL: API_BASE_URL + 'admin/material/del-set/',

    API_WORK_URL: API_BASE_URL + 'work/',
    API_WORK_VIEW_SET_URL: API_BASE_URL + 'admin/work/view-set',
    API_WORK_TO_SET_URL: API_BASE_URL + 'admin/work/to-set/',
    API_WORK_DEL_SET_URL: API_BASE_URL + 'admin/work/del-set/',

    API_CREATE_ESTIMATE_URL: API_BASE_URL + 'estimate/createEstimate/',
    API_GET_ESTIMATE_URL: API_BASE_URL + 'estimate/getEstimate/',
    API_GET_FILE: API_BASE_URL + 'estimate/files/',
    API_GET_SECTION_GROUP: API_BASE_URL + 'SectionGroup/',
}
