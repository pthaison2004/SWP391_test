// API Base URL - Adjust this to match your server configuration
const API_BASE_URL = 'http://localhost:8080/SchoolMedicalSystem';

// API Endpoints
const API_ENDPOINTS = {
    USERS: `${API_BASE_URL}/users`,
    HEALTH_DECLARATIONS: `${API_BASE_URL}/health-declarations`,
    VACCINATIONS: `${API_BASE_URL}/vaccinations`,
    MEDICINE_RECORDS: `${API_BASE_URL}/medicine-records`
};

// Utility Functions
class ApiClient {
    
    // Generic API call method
    static async apiCall(url, method = 'GET', data = null, params = null) {
        try {
            // Add query parameters if provided
            if (params) {
                const urlParams = new URLSearchParams(params);
                url += '?' + urlParams.toString();
            }
            
            const config = {
                method: method,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                }
            };
            
            // Add body for POST, PUT requests
            if (data && (method === 'POST' || method === 'PUT')) {
                if (data instanceof FormData) {
                    config.body = data;
                    delete config.headers['Content-Type']; // Let browser set content-type for FormData
                } else {
                    config.body = new URLSearchParams(data);
                }
            }
            
            const response = await fetch(url, config);
            
            if (!response.ok) {
                const errorData = await response.json().catch(() => ({ error: 'Unknown error' }));
                throw new Error(errorData.error || `HTTP error! status: ${response.status}`);
            }
            
            return await response.json();
            
        } catch (error) {
            console.error('API call failed:', error);
            throw error;
        }
    }
    
    // Show success message
    static showSuccess(message, elementId = 'successMessage') {
        const element = document.getElementById(elementId);
        if (element) {
            element.textContent = message;
            element.style.display = 'block';
            setTimeout(() => {
                element.style.display = 'none';
            }, 5000);
        }
    }
    
    // Show error message
    static showError(message, elementId = 'errorMessage') {
        const element = document.getElementById(elementId);
        if (element) {
            element.textContent = message;
            element.style.display = 'block';
            setTimeout(() => {
                element.style.display = 'none';
            }, 5000);
        }
    }
    
    // Get form data as object
    static getFormData(formId) {
        const form = document.getElementById(formId);
        const formData = new FormData(form);
        const data = {};
        
        for (let [key, value] of formData.entries()) {
            data[key] = value;
        }
        
        return data;
    }
}

// User API Functions
class UserAPI {
    
    // Register new user
    static async register(userData) {
        userData.action = 'register';
        return await ApiClient.apiCall(API_ENDPOINTS.USERS, 'POST', userData);
    }
    
    // Login user
    static async login(username, password) {
        const data = {
            action: 'login',
            username: username,
            password: password
        };
        return await ApiClient.apiCall(API_ENDPOINTS.USERS, 'POST', data);
    }
    
    // Get all users
    static async getAllUsers() {
        return await ApiClient.apiCall(API_ENDPOINTS.USERS, 'GET', null, { action: 'getAll' });
    }
    
    // Get user by ID
    static async getUserById(userId) {
        return await ApiClient.apiCall(API_ENDPOINTS.USERS, 'GET', null, { action: 'getById', id: userId });
    }
    
    // Get users by type
    static async getUsersByType(userType) {
        return await ApiClient.apiCall(API_ENDPOINTS.USERS, 'GET', null, { action: 'getByType', type: userType });
    }
    
    // Update user
    static async updateUser(userId, userData) {
        userData.id = userId;
        return await ApiClient.apiCall(API_ENDPOINTS.USERS, 'PUT', userData);
    }
    
    // Delete user
    static async deleteUser(userId) {
        return await ApiClient.apiCall(API_ENDPOINTS.USERS, 'DELETE', null, { id: userId });
    }
}

// Health Declaration API Functions
class HealthDeclarationAPI {
    
    // Create health declaration
    static async create(declarationData) {
        return await ApiClient.apiCall(API_ENDPOINTS.HEALTH_DECLARATIONS, 'POST', declarationData);
    }
    
    // Get all health declarations
    static async getAll() {
        return await ApiClient.apiCall(API_ENDPOINTS.HEALTH_DECLARATIONS, 'GET', null, { action: 'getAll' });
    }
    
    // Get health declaration by ID
    static async getById(declarationId) {
        return await ApiClient.apiCall(API_ENDPOINTS.HEALTH_DECLARATIONS, 'GET', null, { action: 'getById', id: declarationId });
    }
    
    // Get health declarations by student ID
    static async getByStudentId(studentId) {
        return await ApiClient.apiCall(API_ENDPOINTS.HEALTH_DECLARATIONS, 'GET', null, { action: 'getByStudentId', studentId: studentId });
    }
    
    // Get health declarations by status
    static async getByStatus(status) {
        return await ApiClient.apiCall(API_ENDPOINTS.HEALTH_DECLARATIONS, 'GET', null, { action: 'getByStatus', status: status });
    }
    
    // Update health declaration
    static async update(declarationId, declarationData) {
        declarationData.id = declarationId;
        return await ApiClient.apiCall(API_ENDPOINTS.HEALTH_DECLARATIONS, 'PUT', declarationData);
    }
    
    // Update health declaration status
    static async updateStatus(declarationId, status) {
        const data = {
            action: 'updateStatus',
            id: declarationId,
            status: status
        };
        return await ApiClient.apiCall(API_ENDPOINTS.HEALTH_DECLARATIONS, 'PUT', data);
    }
    
    // Delete health declaration
    static async delete(declarationId) {
        return await ApiClient.apiCall(API_ENDPOINTS.HEALTH_DECLARATIONS, 'DELETE', null, { id: declarationId });
    }
}

// Vaccination API Functions
class VaccinationAPI {
    
    // Create vaccination record
    static async create(vaccinationData) {
        return await ApiClient.apiCall(API_ENDPOINTS.VACCINATIONS, 'POST', vaccinationData);
    }
    
    // Get all vaccinations
    static async getAll() {
        return await ApiClient.apiCall(API_ENDPOINTS.VACCINATIONS, 'GET', null, { action: 'getAll' });
    }
    
    // Get vaccination by ID
    static async getById(vaccinationId) {
        return await ApiClient.apiCall(API_ENDPOINTS.VACCINATIONS, 'GET', null, { action: 'getById', id: vaccinationId });
    }
    
    // Get vaccinations by student ID
    static async getByStudentId(studentId) {
        return await ApiClient.apiCall(API_ENDPOINTS.VACCINATIONS, 'GET', null, { action: 'getByStudentId', studentId: studentId });
    }
    
    // Get vaccinations by status
    static async getByStatus(status) {
        return await ApiClient.apiCall(API_ENDPOINTS.VACCINATIONS, 'GET', null, { action: 'getByStatus', status: status });
    }
    
    // Get vaccinations by vaccine name
    static async getByVaccineName(vaccineName) {
        return await ApiClient.apiCall(API_ENDPOINTS.VACCINATIONS, 'GET', null, { action: 'getByVaccineName', vaccineName: vaccineName });
    }
    
    // Update vaccination
    static async update(vaccinationId, vaccinationData) {
        vaccinationData.id = vaccinationId;
        return await ApiClient.apiCall(API_ENDPOINTS.VACCINATIONS, 'PUT', vaccinationData);
    }
    
    // Update vaccination status
    static async updateStatus(vaccinationId, status) {
        const data = {
            action: 'updateStatus',
            id: vaccinationId,
            status: status
        };
        return await ApiClient.apiCall(API_ENDPOINTS.VACCINATIONS, 'PUT', data);
    }
    
    // Delete vaccination
    static async delete(vaccinationId) {
        return await ApiClient.apiCall(API_ENDPOINTS.VACCINATIONS, 'DELETE', null, { id: vaccinationId });
    }
}

// Medicine Record API Functions
class MedicineRecordAPI {
    
    // Create medicine record
    static async create(recordData) {
        return await ApiClient.apiCall(API_ENDPOINTS.MEDICINE_RECORDS, 'POST', recordData);
    }
    
    // Get all medicine records
    static async getAll() {
        return await ApiClient.apiCall(API_ENDPOINTS.MEDICINE_RECORDS, 'GET', null, { action: 'getAll' });
    }
    
    // Get medicine record by ID
    static async getById(recordId) {
        return await ApiClient.apiCall(API_ENDPOINTS.MEDICINE_RECORDS, 'GET', null, { action: 'getById', id: recordId });
    }
    
    // Get medicine records by student ID
    static async getByStudentId(studentId) {
        return await ApiClient.apiCall(API_ENDPOINTS.MEDICINE_RECORDS, 'GET', null, { action: 'getByStudentId', studentId: studentId });
    }
    
    // Get active medicine records by student ID
    static async getActiveByStudentId(studentId) {
        return await ApiClient.apiCall(API_ENDPOINTS.MEDICINE_RECORDS, 'GET', null, { action: 'getActiveByStudentId', studentId: studentId });
    }
    
    // Get medicine records by status
    static async getByStatus(status) {
        return await ApiClient.apiCall(API_ENDPOINTS.MEDICINE_RECORDS, 'GET', null, { action: 'getByStatus', status: status });
    }
    
    // Update medicine record
    static async update(recordId, recordData) {
        recordData.id = recordId;
        return await ApiClient.apiCall(API_ENDPOINTS.MEDICINE_RECORDS, 'PUT', recordData);
    }
    
    // Update medicine record status
    static async updateStatus(recordId, status) {
        const data = {
            action: 'updateStatus',
            id: recordId,
            status: status
        };
        return await ApiClient.apiCall(API_ENDPOINTS.MEDICINE_RECORDS, 'PUT', data);
    }
    
    // Delete medicine record
    static async delete(recordId) {
        return await ApiClient.apiCall(API_ENDPOINTS.MEDICINE_RECORDS, 'DELETE', null, { id: recordId });
    }
}

// Session Management
class SessionManager {
    
    // Save user session
    static saveSession(user) {
        localStorage.setItem('currentUser', JSON.stringify(user));
        sessionStorage.setItem('isLoggedIn', 'true');
    }
    
    // Get current user
    static getCurrentUser() {
        const userJson = localStorage.getItem('currentUser');
        return userJson ? JSON.parse(userJson) : null;
    }
    
    // Check if user is logged in
    static isLoggedIn() {
        return sessionStorage.getItem('isLoggedIn') === 'true' && this.getCurrentUser() !== null;
    }
    
    // Logout user
    static logout() {
        localStorage.removeItem('currentUser');
        sessionStorage.removeItem('isLoggedIn');
        window.location.href = 'login.html';
    }
    
    // Check authentication and redirect if needed
    static requireAuth(allowedUserTypes = []) {
        if (!this.isLoggedIn()) {
            window.location.href = 'login.html';
            return false;
        }
        
        if (allowedUserTypes.length > 0) {
            const user = this.getCurrentUser();
            if (!allowedUserTypes.includes(user.userType)) {
                alert('Bạn không có quyền truy cập trang này!');
                window.location.href = 'dashBoard.html';
                return false;
            }
        }
        
        return true;
    }
}
