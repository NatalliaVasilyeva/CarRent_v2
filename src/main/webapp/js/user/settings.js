function changeLocale(select) {
    console.log(select.value);
    $.post('change-language',
        {
            language: select.value
        },
        () => location.reload());
}

function changePassword() {
    $('#change-password-div').hide();
    $('#save-new-password-div').show();
}

function changeLanguage() {
    $('#change-language-div').hide();
    $('#save-new-language-div').show();
}


function cancelChangePassword() {
    $('#change-password-div').show();
    $('#save-new-password-div').hide();
}

function cancelChangeLanguage() {
    $('#change-language-div').show();
    $('#save-new-language-div').hide();
}


function validateChangePasswordForm(oldPassword, newPassword, confirmNewPassword) {
    let errorMessage = {};
    if (!oldPassword) {
        errorMessage.oldPassword = 'Old Password is empty';
    }
    if (!newPassword) {
        errorMessage.newPassword = 'New Password is empty';
    }
    if (!confirmNewPassword) {
        errorMessage.confirmNewPassword = 'Confirm New Password is empty';
    }
    if (confirmNewPassword !== newPassword) {
        const msg = 'New Password and Confirm New Password do not match';
        errorMessage.newPassword = msg;
        errorMessage.confirmNewPassword = msg;
    }
    console.log(oldPassword);
    console.log(newPassword);
    console.log(confirmNewPassword);
    console.log(errorMessage);
    showSettingsErrors(
        errorMessage.oldPassword,
        errorMessage.newPassword,
        errorMessage.confirmNewPassword
    );
    return $.isEmptyObject(errorMessage);
}

function onSuccessfulChangePassword(data) {
    clearSettingsInput();
    cancelChangePassword();
}

function showSettingsErrors(oldMsg, newMsg, confMsg) {
    showOneError($('#settings-old-password-input'), $('#settings-old-password-error-small'), oldMsg);
    showOneError($('#settings-new-password-input'), $('#settings-new-password-error-small'), newMsg);
    showOneError($('#settings-confirm-new-password-input'), $('#settings-confirm-new-password-error-small'), confMsg);
}

function showOneError(input, error, msg) {
    if (msg) {
        input.addClass('is-invalid');
        error.text(msg);
    } else {
        input.removeClass('is-invalid');
        error.text('');
    }
}

function clearSettingsInput() {
    let oldInput = $('#settings-old-password-input');
    let newInput = $('#settings-new-password-input');
    let conInput = $('#settings-confirm-new-password-input');
    let oldError = $('#settings-old-password-error-small');
    let newError = $('#settings-new-password-error-small');
    let conError = $('#settings-confirm-new-password-error-small');
    oldInput.val('');
    newInput.val('');
    conInput.val('');
    showOneError(oldInput, oldError, null);
    showOneError(newInput, newError, null);
    showOneError(conInput, conError, null);
}