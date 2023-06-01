
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