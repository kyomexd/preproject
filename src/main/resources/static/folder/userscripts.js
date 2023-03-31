var stompClient = null;
var socket = new SockJS('/ws');
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
    console.log(frame);
});

function showRequestmodal() {
    $('#request').modal('show')
}

function hideRequestButton() {
    document.getElementById('showRequestModal').setAttribute('style', "float: bottom; display: none; align-items: center;")
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
        url: "/requests/save",
        data: requestJson,
        contentType: "application/json; charset=utf8",
        success: function () {
            document.getElementById('close-request-modal').click()
            hideRequestButton()
            stompClient.send('/app/application', {}, requestJson)
        },
        error: function () {
            console.log('error while saving request')
            alert('Bad credentials, please try again')
        }
    })
}