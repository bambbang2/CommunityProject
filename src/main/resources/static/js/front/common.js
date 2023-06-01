$("#userId").val(Cookies.get('key'));
    if($("#userId").val() != ""){
        $("#savedId").attr("checked", true);
    }

$("#idSaveCheck").change(function(){
    if($("#savedId").is(":checked")){
        Cookies.set('key', $("#userId").val(), { expires: 15 });
    }else{
          Cookies.remove('key');
    }
});

$("#userId").keyup(function(){
    if($("#savedId").is(":checked")){
        Cookies.set('key', $("#userId").val(), { expires: 15 });
    }
});
