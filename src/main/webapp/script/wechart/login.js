$(function(){
    //  点击定位取消定位
     $('.positioning').click(function(){
        if( $(this).hasClass('bg_blue')){
              $(this).removeClass('bg_blue').text("取消定位").addClass('bg_red')
        }else {
              $(this).addClass('bg_blue').text("定位").removeClass('bg_red');
        } 
     });
    // 点击选择您居住小区 弹出小区模态框
   0
    // 
     $('.section_five').click(function(){
         if($('#residentail').val() == 0) {
            dialogAlert("请选择小区");
         }else{
            //  console.log(2222);
             
          $('.choose_community').hide();
          $('.choose_room').show();
          $(' .line-one').removeClass('active').addClass('actived');
          $('.line-two').addClass('active');
        //  点击上一页按钮
         $('.oBn_l').click(function(){
             $('.choose_community').show();
             $('.choose_room').show();
             $(' .line-one').removeClass('actived').addClass('active');
             $('.line-two').removeClass('active')
           })
         }
     })


 })
 