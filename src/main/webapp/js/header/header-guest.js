
$(function () {
    $("#sign-in-modal").on("hidden.bs.modal", clearSignInInput);
    $("#sign-up-modal").on("hidden.bs.modal", clearSignUpInput);
});