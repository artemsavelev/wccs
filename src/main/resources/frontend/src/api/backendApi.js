const API_BASE_URL = 'http://0.0.0.0:8080/api/v1/';
export default {
    API_AUTH_URL: API_BASE_URL + 'auth/login',
    API_ADMIN_URL: API_BASE_URL + 'admin/users',
    API_ORDER_URL: API_BASE_URL + 'order/',
    API_ORDER_PAGE_URL: API_BASE_URL + 'order/?page=',
    API_USER_URL: API_BASE_URL + 'user/',
    API_DEVICE_URL: API_BASE_URL + 'device/',
    API_MATERIAL_URL: API_BASE_URL + 'material/',
    API_WORK_URL: API_BASE_URL + 'work/',
    API_CREATE_ESTIMATE_URL: API_BASE_URL + 'estimate/createEstimate/',
    API_GET_ESTIMATE_URL: API_BASE_URL + 'estimate/getEstimate/',
    API_GET_FILE: API_BASE_URL + 'estimate/files/',
}