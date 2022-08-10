$(function () {
    $(".makeeditablebtn").click(function () {
        setInputsReadOnly(false);
        $('#edit-profile-button-div').hide();
        $('#edit-manage-buttons-div').show();
    })
});

$(function () {
    $(".makeuneditablebtn").click(function () {
        setInputsReadOnly(true);
        $('#edit-profile-button-div').show();
        $('#edit-manage-buttons-div').hide();
    })
});

function setInputsReadOnly(isReadOnly) {
    $('#profile-firstname-input').prop('readonly', isReadOnly);
    $('#profile-surname-input').prop('readonly', isReadOnly);
    $('#profile-email-input').prop('readonly', isReadOnly);
    $('#profile-address-input').prop('readonly', isReadOnly);
    $('#profile-phone-input').prop('readonly', isReadOnly);
    $('#profile-driver-license-number-input').prop('readonly', isReadOnly);
    $('#profile-driver-license-issue-date-input').prop('readonly', isReadOnly);
    $('#profile-driver-license-expired-date-input').prop('readonly', isReadOnly);

}