var tempid = 1000;
function dialogAlert(msg, title,buttonName) {
    if (title == undefined) {
        title = "";
    }
    if (buttonName==undefined){
        buttonName = '确定';
    }
    var tempId = tempid++;
    var html = '<div class="popUp" id="dialog_' + tempId + '"class="dialog"><div><h5 class="popUp-title">' + title + '</h5><div class="popUp-content">' + msg + '</div>' +
        '<div class="popUp-btn"><span class="btn-1" onclick="closeDialog(\'dialog_' + tempId + '\')">'+buttonName+'</span></div></div></div>';
    $('body').append(html);
    $('#dialog_' + tempId).show();
    return tempId;
}

function processing(msg, title) {
    if (title == undefined) {
        title = "";
    }
    var tempId = tempid++;
    var html = '<div class="popUp" id="dialog_' + tempId + '"class="dialog"><div><h5 class="popUp-title">' + title + '</h5><div class="popUp-content">' + msg + '</div>' +
        '</div></div>';
    $('body').append(html);
    $('#dialog_' + tempId).show();
    return 'dialog_' + tempId;
}

function dialogAlertWait(msg, func, title,buttonName) {
    if (title == undefined) {
        title = "";
    }
    if (buttonName==undefined){
        buttonName = '确定';
    }
    var tempId = tempid++;
    var html = '<div class="popUp" id="dialog_' + tempId + '"class="dialog"><div><h5 class="popUp-title">' + title + '</h5><div class="popUp-content">' + msg + '</div>' +
        '<div class="popUp-btn"><span class="btn-1" id="temp_dialog_confirm_w">'+buttonName+'</span></div></div></div>';
    $('body').append(html);
    $('#dialog_' + tempId).show();
    $('#temp_dialog_confirm_w').click(function () {
        closeDialog('dialog_' + tempId);
        if (typeof(func) == 'function') {
            func();
        }
    });
    return tempId;
}

function dialogConfirm(msg, func, title,noBtn,okBtn) {
    if (title == undefined) {
        title = "";
    }
    if(noBtn==undefined){
        noBtn = "取消";
    }
    if (okBtn==undefined){
        okBtn="确定";
    }

    var tempId = tempid++;
    var html = '<div class="popUp" id="dialog_' + tempId + '"class="dialog"><div><h5 class="popUp-title">' + title + '</h5><div class="popUp-content">' + msg + '</div>' +
        '<div class="popUp-btn"><span class="btn-2" onclick="closeDialog(\'dialog_' + tempId + '\')">'+noBtn+'</span><span class="btn-1" id="temp_dialog_confirm">'+okBtn+'</span></div></div></div>';
    $('body').append(html);
    $('#dialog_' + tempId).show();
    $('#temp_dialog_confirm').click(function () {
        closeDialog('dialog_' + tempId);
        if (typeof(func) == 'function') {
            func();
        }
    });
    return tempId;
}

var defaultOptons = {
    cancelName: '取消',
    confirmName: '确定',
    title: ''
}



function dialogCommon(option){


}

function closeDialog(id) {
    $('#' + id).remove();
}