import axios from 'axios'

const API_URL = 'http://localhost:8399/'

class AuthService {
    // Login
    login(user){
        return axios
            .post(API_URL+'signin',{
                email: user.email,
                password: user.password
            })
            .then(this.handelResponse)
            .then(response => {
                if(response.data.accessToken) {
                    localStorage.setItem('user', JSON.stringify(response.data))
                }
                return response.data
            })
    }
    // Logout
    logout() {
        localStorage.removeItem('user')
    }
    // C: User
    register(user){
        return axios.post(API_URL + 'api/v1/', {
            nickname: user.username,
            email: user.email,
            password: user.password
        })
    }
    // R: User
    detail(user){
        return axios.get(API_URL + `api/v1/${user.id}`)
    }
    // U: User
    update(){
        return axios.put(API_URL + 'api/v1/', {
            // Body, 유저가 수정한 정보만 업데이트
            // 백엔드랑 상의하고 정하자
        })
    }
    // D: User
    delete(user){
        // uid를 이렇게 확인해서 받아온 뒤에 가야하나?
        // let tmp_uid = 
        // if (tmp_uid !== null) {}
        return axios.delete(API_URL + `api/v1/${user.id}`)
    }


    // ??
    handelResponse(response) {
        if(response.status === 401) {
            this.logout()
            location.reload(true)
            const error = response.data && response.data.message
            return Promise.reject(error)
        }
        return Promise.resolve(response)
    }
}

export default new AuthService()