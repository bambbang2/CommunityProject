
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


    /** 회원 탈퇴 버튼 S */
//    document.getElementById('deleteBtn').addEventListener("click", function() {
//
//        var userNo = (this).data('userNo');
//        console.log(userNo);
//        var isConfirmed = confirm("회원 탈퇴 처리하시겠습니까?");
//
//        if (isConfirmed) {
//
//            $.ajax({
//                url:'/api/member/delete' + userNo,
//                type: 'DELETE',
//                success:function(result){
//                alert('삭제 완료!');
//                },
//                error:function(xhr, status, error) {
//                alert('삭제에 실패하였습니다.');
//                }
//
//            })
//        }
//    });
//    $(document).ready(function() {
//        $('#deleteBtn').click(function() {
//            var userNo = $(this).data('userNo');
//
//            if (confirm('정말 탈퇴하시겠습니까?')) {
//                $.ajax({
//                    url:'/api/member/delete' + userNo,
//                    type:'DELETE',
//                    success:function(result) {
//                        alert('탈퇴 완료');
//                        location.href='/admin/member';
//                    },
//                    error:function(xhr, status, error) {
//                        console.log(error);
//                        alert('탈퇴 실패');
//                        history.back();
//                    }
//
//                })
//            }
//
//        })
//    })


});


//$(document).ready(function() {
//        $('#deleteBtn').click(function() {
//            var userNo = $(this).data('userNo');
//
//            if (confirm('정말 탈퇴하시겠습니까?')) {
//                $.ajax({
//                    url:'/api/member/delete' + userNo,
//                    type:'DELETE',
//                    success:function(result) {
//                        alert('탈퇴 완료');
//                        location.href='/admin/member';
//                    },
//                    error:function(xhr, status, error) {
//                        console.log(error);
//                        alert('탈퇴 실패');
//                        history.back();
//                    }
//
//                });
//            }
//
//        });
//    });


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