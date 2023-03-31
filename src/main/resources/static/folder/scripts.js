window.onload = function () {
    getPendingRequests()
    $.ajax({
        method: 'GET',
        url: '/admin/table',
        contentType: 'application/json',
        success: function (response) {
            drawTable(response)
        },
        error: function (error) {
            console.log(error);
        }
    })
}

var stompClient = null;

var socket = new SockJS('/ws');
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    console.log(frame);
    stompClient.subscribe('/all/messages', function (result) {
        let data = JSON.parse(result.body)
        alertRequest(data.headers.simpUser.principal.email)
    });
});

function getPendingRequests() {
    $.ajax({
        type: 'GET',
        url: '/requests/pending',
        contentType: 'application/json',
        async: false,
        success: function (data) {
            if (data !== 0) {
                document.getElementById('alert_bar').innerHTML = "Requests " +
                    "<i id='alert_icon' class='fa fa-circle' style='color: red'> " + data + " </i>"
                console.log('have pending')
            } else {
                document.getElementById('alert_bar').innerHTML = "Requests " +
                    "<i id='alert_icon' class='fa fa-circle' style='color: limegreen'></i>"
                console.log('no pending')
            }
        }
    })
}
function alertRequest(email) {
    hideAlertRequest()
    document.getElementById('request-alert').style.display = "block"
    document.getElementById('request-alert').innerHTML = "New request from user " + email +
        "<button type='button' class='close' aria-label='Close' onclick='hideAlertRequest()'><span aria-hidden='true'>&times;</span></button>"
    setTimeout(function () {
        hideAlertRequest()
    }, 5000)
    refreshRequestsTable()
    getPendingRequests()
}

function hideAlertRequest() {
    document.getElementById('request-alert').style.display = "none"
}
function showPassword() {
    let togglePasswordShow = document.getElementById('show-password');
    let togglePasswordHide = document.getElementById('hide-password')
    let password = document.getElementById('new-form-password');
    let type = password.getAttribute("type") === "password" ? "text" : "password";
    password.setAttribute("type", type);
    togglePasswordShow.setAttribute("height", "0")
    togglePasswordShow.setAttribute("width", "0")
    togglePasswordShow.setAttribute("display", "none")
    togglePasswordHide.setAttribute("height", "16")
    togglePasswordHide.setAttribute("width", "16")
    togglePasswordHide.setAttribute("display", "block")
}

function hidePassword() {
    let togglePasswordShow = document.getElementById('show-password');
    let togglePasswordHide = document.getElementById('hide-password')
    let password = document.getElementById('new-form-password');
    let type = password.getAttribute("type") === "password" ? "text" : "password";
    password.setAttribute("type", type);
    togglePasswordShow.setAttribute("height", "16")
    togglePasswordShow.setAttribute("width", "16")
    togglePasswordShow.setAttribute("display", "block")
    togglePasswordHide.setAttribute("display", "none")
    togglePasswordHide.setAttribute("height", "0")
    togglePasswordHide.setAttribute("width", "0")
}

function resetNewUserForm() {
    document.getElementById('new-form-name').value = ""
    document.getElementById('new-form-age').value = ""
    document.getElementById('new-form-email').value = ""
    document.getElementById('new-form-password').value = ""
    document.getElementById('new-role-admin').checked = false
    document.getElementById('new-role-user').checked = false
}

function addUser() {
    let hasUser = document.getElementById('new-role-user').checked === true;
    let hasAdmin = document.getElementById('new-role-admin').checked === true;
    let user = {
        name: document.getElementById('new-form-name').value,
        age: document.getElementById('new-form-age').value,
        email: document.getElementById('new-form-email').value,
        password: document.getElementById('new-form-password').value,
        city: document.getElementById('new-form-city').value,
        hasUser: hasUser,
        hasAdmin: hasAdmin
    }
    let userjson = JSON.stringify(user)
    $.ajax({
        method: 'POST',
        url: '/admin/add',
        data: userjson,
        contentType: "application/json; charset=utf8",
        success: function () {
            document.getElementById("home-tab").click();
        },
        error: function () {
            console.log('error from adding new user')
            alert('Bad Credentials. Please try again')
        }
    })

}

function refreshTable() {
    $("#all_users_table td").remove();
    $.ajax({
        method: 'GET',
        url: '/admin/table',
        contentType: 'application/json',
        success: function (response) {
            drawTable(response)
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function refreshRequestsTable() {
    getPendingRequests()
    $("#all_requests_table td").remove();
    $.ajax({
        method: 'GET',
        url: '/requests',
        contentType: 'application/json',
        success: function (response) {
            drawRequestTable(response)
        },
        error: function (error) {
            console.log(error)
        }
    })
}

function drawTable(data) {
    for (let i = 0; i < data.length; i++) {
        addRow(data[i]);
    }
}

function drawRequestTable(data) {
    for (let i=0; i < data.length; i++) {
        addRequestRow(data[i]);
    }
}

function addRow(data) {
    let table = document.getElementById("all_users_table").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let roles = "";

    insertTd(data.id, tr)
    insertTwitchAvatar(data.name, tr)
    insertTd(data.name, tr)
    insertTd(data.age, tr)
    insertTd(data.email, tr)
    insertTd(data.city, tr)
    getWeather(data.city,tr)
    for (let i = 0; i < data.roles.length; i++) {
        roles += (data.roles[i].role.replace("ROLE_", "")) + " "
    }
    insertTd(roles, tr)
    insertEditBtn(data.id, roles, tr)
    insertDelBtn(data, roles, tr)
}

function addRequestRow(data) {
    let table = document.getElementById('all_requests_table').getElementsByTagName('tbody')[0];
    let tr = table.insertRow(table.rows.length)

    insertTd(data.id, tr)
    insertTd(data.username, tr)
    insertTd(data.message, tr)
    insertTd(data.resolved, tr)
    insertApproveButton(data.id, data.username, data.message, data.resolved, tr)
    insertDeclineButton(data.id, data.username, data.message, data.resolved, tr)
}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element);
}

function insertTwitchAvatar(username, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    $.ajax({
        method: 'GET',
        url: 'https://api.twitch.tv/helix/users',
        headers: {
            'Authorization': 'Bearer beu6ej5kthkly4ogl5yc4gr8uph9t4',
            'Client-Id': 't3g00934bflj02k4lgyatlpdzs52c1'
        },
        data: {
            login: username
        },
        success: function (data) {
            element.innerHTML = "<img src= '" + data.data[0].profile_image_url + "' width='64' height='64'>";
        },
        error: function (error) {
            element.innerText = '?';
        }
    })
    parent.insertAdjacentElement("beforeend", element);
}

function insertDelBtn(data, roles, parent) {
    let td = document.createElement("td");
    td.scope = "row";
    let element = document.createElement("button");
    element.innerText = "Delete";
    element.type = "submit";
    element.className = "btn btn-outline-dark"
    element.addEventListener('click', () => {
        showDeleteModal(data, roles)
    })
    td.appendChild(element);
    parent.insertAdjacentElement("beforeend", td);
}

function insertEditBtn(id, roles, parent) {
    let td = document.createElement("td");
    td.scope = "row";
    let element = document.createElement("button");
    element.innerHTML = "<button type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\"#editUser\">"
    element.innerText = "Edit";
    element.className = "btn btn-outline-dark";
    element.addEventListener('click', () => {
        showUserModal(id, roles)
    })
    td.appendChild(element);
    parent.insertAdjacentElement("beforeend", td);
}

function insertApproveButton(id, email, message, resolved, parent) {
    let td = document.createElement("td");
    td.scope = "row";
    let element = document.createElement("button");
    element.innerHTML = '<button>'
    element.disabled = true;
    if (!resolved) {
        element.disabled = false;
        element.addEventListener('click', () => {
            showCaptchaModal(id, email, message, "approved")
        })
    }
    element.innerText = "Approve";
    element.className = "btn btn-outline-success";
    td.appendChild(element);
    parent.insertAdjacentElement("beforeend", td);
}

function insertDeclineButton(id, email, message, resolved, parent) {
    let td = document.createElement("td");
    td.scope = "row";
    let element = document.createElement("button");
    element.innerHTML = '<button>'
    element.disabled = true;
    if (!resolved) {
        element.disabled = false;
        element.addEventListener('click', () => {
            showCaptchaModal(id, email, message, "denied")
        })
    }
    element.innerText = "Decline";
    element.className = "btn btn-outline-danger";
    td.appendChild(element);
    parent.insertAdjacentElement("beforeend", td);
}

function approveRequest(id) {
    $.ajax({
        method: 'POST',
        url: "/requests/accept/" + id.toString(),
        success: function () {
            console.log('approved')
            refreshRequestsTable()
        },
        error: function (error) {
            console.log(error)
        }
    })
}

function declineRequest(id) {
    $.ajax({
        method: 'POST',
        url: "/requests/decline/" + id.toString(),
        success: function () {
            console.log('declined')
            refreshRequestsTable()
        },
        error: function (error) {
            console.log(error)
        }
    })
}

function getWeather(city,parent) {
    return (
        $.ajax({
            method: 'GET',
            async:false,
            url: 'http://api.weatherapi.com/v1/current.json',
            headers: {
                'Access-Control-Allow-Origin': 'http://localhost:8080',
                'Access-Control-Allow-Credentials': 'true'
            },
            data: {
                key: '243b055b2a7a4fffa53205024232103',
                q: city
            },
            contentType: 'application/json',
            success: function (response) {
                console.log(response.current.condition)
                let element = document.createElement("td");
                element.scope = "row";
                element.innerHTML = "<img src= '" +  response.current.condition.icon + "'>"
                parent.insertAdjacentElement("beforeend", element);
            },
            error: function (e) {
                console.log(e)
                let element = document.createElement("td");
                element.scope = "row";
                element.innerText = "undefined"
                parent.insertAdjacentElement("beforeend", element);
            }
        }))
}

function showUserModal(id, roles) {
    drawEditModal(id, roles)
    $('#editUser').modal('show');
}

function showDeleteModal(data, roles) {
    drawDeleteModal(data, roles)
    $('#deleteUser').modal('show');
}

function showCaptchaModal(requestid, requestemail, requestmsg, verdict) {
    $.ajax({
        type: 'GET',
        url: '/dvach/captcha',
        success: function (id) {
            document.getElementById('captcha-id').value = requestid;
            document.getElementById('captcha-email').value = requestemail;
            document.getElementById('captcha-msg').value = requestmsg;
            document.getElementById('captcha-value').value = null;
            document.getElementById('captcha-verdict').value = verdict;
            document.getElementById('captcha-img').src = "https://2ch.hk/api/captcha/2chcaptcha/show?id=" + id;
        }
    })
    $('#captcha').modal('show')
}

function refreshCaptcha() {
    $.ajax({
        type: 'GET',
        url: '/dvach/captcha',
        async: false,
        success: function (id) {
            document.getElementById('captcha-img').src = "https://2ch.hk/api/captcha/2chcaptcha/show?id=" + id;
        }
    })
}

function sendDvachRequest() {
    var captcha = {
        id: document.getElementById('captcha-img').src.replace('https://2ch.hk/api/captcha/2chcaptcha/show?id=', ''),
        value: document.getElementById('captcha-value').value,
        comment: 'Request ID: ' + document.getElementById('captcha-id').value + '\n'
            + 'From user: ' + document.getElementById('captcha-email').value + '\n'
            + 'Request message: ' + document.getElementById('captcha-msg').value + '\n'
            + 'Request ' + document.getElementById('captcha-verdict').value
    }
    let captchajson = JSON.stringify(captcha);
    $.ajax({
        type: 'POST',
        url: '/dvach/logs',
        data: captchajson,
        contentType: "application/json; charset=utf8",
        async: false,
        success: function (response) {
            if (response.contains('error')) {
                alert('Bad value for captcha. Please, try again')
                refreshCaptcha()
            } else {
                if (document.getElementById('captcha-verdict').value === 'approved') {
                    approveRequest(document.getElementById('captcha-id').value)
                } else {
                    declineRequest(document.getElementById('captcha-id').value)
                }
                document.getElementById('close-captcha-modal').click()
            }
        },
        error: function () {
            console.log('error from sending request to 2ch.hk')
            alert('Bad Credentials. Please try again')
        }
    })
}

String.prototype.contains = function (word) {
    var regex = new RegExp('\\b' + word + '\\b');
    return regex.test(this);
};

function drawEditModal(id, roles) {
    $.ajax({
        method: 'GET',
        url: '/admin/edit/' + id.toString(),
        contentType: 'application/json',
        success: function (data) {
            let hasUser = false;
            let hasAdmin = false;
            document.getElementById('title').innerText = data.email;
            document.getElementById('name').value = data.name;
            document.getElementById('age').value = data.age;
            document.getElementById('email').value = data.email;
            document.getElementById('city').value = data.city;
            document.getElementById('role-user').checked = !!roles.contains('USER');
            document.getElementById('role-admin').checked = !!roles.contains('ADMIN');
            document.getElementById('save_changes_button').onclick = (function () {
                hasUser = document.getElementById('role-user').checked === true;
                hasAdmin = document.getElementById('role-admin').checked === true;
                var user = {
                    id: id,
                    name: document.getElementById('name').value,
                    age: document.getElementById('age').value,
                    email: document.getElementById('email').value,
                    city: document.getElementById('city').value,
                    hasUser: hasUser,
                    hasAdmin: hasAdmin
                }
                console.log(user)
                let userjson = JSON.stringify(user)
                $.ajax({
                    method: 'POST',
                    url: "/admin/edit/" + id.toString(),
                    data: userjson,
                    contentType: "application/json; charset=utf8",
                    success: function () {
                        document.getElementById('close-edit-modal').click()
                        refreshTable()
                    },
                    error: function () {
                        console.log('error from saving edited user')
                        alert('Bad Credentials. Please try again')
                    }
                })
            })
        }
    })
}

function drawDeleteModal(data, roles) {
    document.getElementById('delete-title').innerText = data.email;
    document.getElementById('delete-name').value = data.name;
    document.getElementById('delete-age').value = data.age;
    document.getElementById('delete-email').value = data.email;
    document.getElementById('delete-city').value = data.city;
    document.getElementById('delete-role-user').checked = !!roles.contains('USER');
    document.getElementById('delete-role-admin').checked = !!roles.contains('ADMIN');
    document.getElementById('delete_button').onclick = (function () {
        $.ajax({
            method: 'GET',
            url: "/admin/delete/" + data.id,
            success: function () {
                document.getElementById('close-delete-modal').click()
                refreshTable()
            }
        })
    })
}
