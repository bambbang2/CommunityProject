/** 게시판 작서으 수정 양식 */

window.addEventListener("DOMContentLoaded", function() {
    try {
        CKEDITOR.replace("content", {
            height: 350
        });
    } catch (e) {}
});