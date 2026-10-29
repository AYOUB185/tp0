function copyToClipboard(id) {
    var text = document.getElementById('form:' + id).value;
    navigator.clipboard.writeText(text);
}
function toutEffacer() {
    document.getElementById('form:question').value = '';
    document.getElementById('form:reponse').value = '';
}