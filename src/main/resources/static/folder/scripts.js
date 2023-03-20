window.onload = function () {
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
            refreshTable();
            document.getElementById("home-tab").click();
        },
        error: function () {
            console.log('error from adding new user')
            alert('Bad Credentials. Please try again')
        }
    })

}

function refreshTable() {

    $("#all_users_table tr").remove();
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

function drawTable(data) {
    for (let i = 0; i < data.length; i++) {
        addRow(data[i]);
    }
}

function addRow(data) {
    let table = document.getElementById("all_users_table").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let roles = "";

    insertTd(data.id, tr)
    insertTd(data.name, tr)
    insertTd(data.age, tr)
    insertTd(data.email, tr)
    for (let i = 0; i < data.roles.length; i++) {
        roles += (data.roles[i].role.replace("ROLE_", "")) + " "
    }
    insertTd(roles, tr)
    insertEditBtn(data.id, roles, tr)
    insertDelBtn(data, roles, tr)
}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
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

function showUserModal(id, roles) {
    drawEditModal(id, roles)
    $('#editUser').modal('show');
}

function showDeleteModal(data, roles) {
    drawDeleteModal(data, roles)
    $('#deleteUser').modal('show');
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
                    hasUser: hasUser,
                    hasAdmin: hasAdmin
                }
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
