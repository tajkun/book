window.onload = function () {
    let account = $.cookie('account');
    let nickName = $.cookie('nickName');
    let role = $.cookie('role');
    if (account !== undefined) {
        $('#login').text('欢迎你:' + nickName);
    }
}