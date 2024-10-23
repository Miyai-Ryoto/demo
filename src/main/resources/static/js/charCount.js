function updateCount(textAreaId, countDisplayId) {
    const textArea = document.getElementById(textAreaId);
    const countDisplay = document.getElementById(countDisplayId);
    const maxLength = textArea.getAttribute("maxlength");
    countDisplay.textContent = `${textArea.value.length} / ${maxLength}`;
}

window.onload = function() {
    updateCount("content", "charCount"); // ページロード時に初期値を設定
};