
window.addEventListener("DOMContentLoaded", function() {
    /** 전체 선택 토글 처리 */
    const chkAlls = document.getElementsByClassName("checkall");
    for (const el of chkAlls) {
        el.addEventListener("click", function() {
            const target = this.dataset.targetName;
            if (!target) return;

            const targets = document.getElementsByName(target);
            for (const ta of targets) {
                ta.checked = this.checked;
            }
        });
    }
    /** 전체 선택 토글 처리 E */
});

/**
window.addEventListener("DOMContentLoaded", function() {
    const side = document.getElementById("jsSide");

    function slideDown(){
        side.style.left = "-150px";
    }

    function slideUp(){
        side.style.left = "208px";
    }


    function handleMouseMove(event) {
        let pageX = event.pageX;
        let pageY = event.pageY;

        if (pageX <= 206 && (pageY >= 150 && pageY <= 220)) {
            slideUp();
            console.log("asd");
        } else if (pageX >= 355) {
            slideDown();
        }
    }

    function init() {
        document.addEventListener("mousemove", handleMouseMove);
    }

    init();
});




//const side = document.getElementById('jsSide');
//const list = document.getElementById('jsList');
//
//side.addEventListener('mouseover', (event) => {
//    side.style.left = "0";
//});
//
//side.addEventListener('mouseout', (event) => {
//    side.style.left = "-150px";
//});
*/