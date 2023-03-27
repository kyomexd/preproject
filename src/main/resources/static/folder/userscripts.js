function showRequestmodal() {
    $('#request').modal('show')
}

function sendRequest() {
    var request = {
        username: document.getElementById('email').value,
        message: document.getElementById('message').value,
        resolved: false
    }
    let requestJson = JSON.stringify(request)
    $.ajax({
        method: 'POST',
        url: "/request",
        data: requestJson,
        contentType: "application/json; charset=utf8",
        success: function () {
            document.getElementById('close-request-modal').click()
        },
        error: function () {
            console.log('error while saving request')
            alert('Bad credentials, please try again')
        }
    })
}