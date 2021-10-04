
const host = process.env.NODE_ENV === 'production' ? location.origin : location.origin.slice(0, -5)

const API_BASE_URL = host + ':8080/api/v1/'
console.info('Server address ' + API_BASE_URL)
console.info(process.env.NODE_ENV)

// document.fonts.ready.then(function () {
//     console.info('All fonts in use by visible text have loaded.');
//     console.info('Montserrat loaded? ' + document.fonts.check('1em Montserrat'));  // true
// });

export default {
    host,
    API_REGISTRATION_URL: API_BASE_URL + 'admin/registration',
    API_ADMIN_URL: API_BASE_URL + 'admin/users',
    API_AUTH_URL: API_BASE_URL + 'auth/login',
    API_USER_URL: API_BASE_URL + 'user/',

    // positions
    API_ALL_POSITION_URL: API_BASE_URL + 'admin/all-position/',
    API_GET_POSITION_URL: API_BASE_URL + 'admin/get-position/',
    API_SAVE_POSITION_URL: API_BASE_URL + 'admin/save-position/',
    API_DELETE_POSITION_URL: API_BASE_URL + 'admin/delete-position/',

    // departments
    API_ALL_DEPARTMENT_URL: API_BASE_URL + 'admin/all-department/',
    API_GET_DEPARTMENT_URL: API_BASE_URL + 'admin/get-department/',
    API_SAVE_DEPARTMENT_URL: API_BASE_URL + 'admin/save-department/',
    API_DELETE_DEPARTMENT_URL: API_BASE_URL + 'admin/delete-department/',

    API_ORDER_URL: API_BASE_URL + 'order/',
    API_ORDER_PAGE_URL: API_BASE_URL + 'order/?page=',
    API_ORDER_SEARCH_URL: API_BASE_URL + 'order/search/?q=',

    API_DEVICE_URL: API_BASE_URL + 'device/',
    API_DEVICE_VIEW_SET_URL: API_BASE_URL + 'admin/device/view-set',
    API_DEVICE_TO_SET_URL: API_BASE_URL + 'admin/device/to-set/',
    API_DEVICE_DEL_SET_URL: API_BASE_URL + 'admin/device/del-set/',
    API_DEVICE_UPDATE_URL: API_BASE_URL + 'admin/device/update/',
    API_DEVICE_SAVE_URL: API_BASE_URL + 'admin/device/save/',

    API_MATERIAL_URL: API_BASE_URL + 'material/',
    API_MATERIAL_VIEW_SET_URL: API_BASE_URL + 'admin/material/view-set',
    API_MATERIAL_TO_SET_URL: API_BASE_URL + 'admin/material/to-set/',
    API_MATERIAL_DEL_SET_URL: API_BASE_URL + 'admin/material/del-set/',
    API_MATERIAL_UPDATE_URL: API_BASE_URL + 'admin/material/update/',
    API_MATERIAL_SAVE_URL: API_BASE_URL + 'admin/material/save/',

    API_WORK_URL: API_BASE_URL + 'work/',
    API_WORK_VIEW_SET_URL: API_BASE_URL + 'admin/work/view-set',
    API_WORK_TO_SET_URL: API_BASE_URL + 'admin/work/to-set/',
    API_WORK_DEL_SET_URL: API_BASE_URL + 'admin/work/del-set/',
    API_WORK_UPDATE_URL: API_BASE_URL + 'admin/work/update/',
    API_WORK_SAVE_URL: API_BASE_URL + 'admin/work/save/',

    API_CREATE_ESTIMATE_URL: API_BASE_URL + 'estimate/createEstimate/',
    API_GET_ESTIMATE_URL: API_BASE_URL + 'estimate/get-estimate/',
    // API_GET_ESTIMATE_URL2: API_BASE_URL + 'estimate/get-estimate/?customer={customer}&address={address}',
    // API_GET_FILE: API_BASE_URL + 'estimate/files/',
    // API_GET_FILE: API_BASE_URL + 'estimate/download/?fileName=',
    API_GET_FILE: API_BASE_URL + 'estimate/download/',

    API_GET_SECTION_GROUP: API_BASE_URL + 'SectionGroup/',
}
