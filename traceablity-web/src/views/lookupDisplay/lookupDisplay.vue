<template>
 
  <div class="one" >
    <div class="textOne">
      <h1>源溯</h1>
      <div>基于机器视觉与溯源的农产品销售问题解决方案</div>
    </div>
  </div>
  <div class="two" >
    <div class="textTwo">
      <h1>建模信息</h1>
      <div>展现苹果实景建模</div>
    </div>
    <video controls  preload="auto" autoplay muted >
      <source src="../../assets/display/建模信息.mp4" type="video/mp4">
    </video>
  </div>
  <div class="three" >
      <div class="textThree">
      <h1>苹果品质</h1>
      <div class="bottom">
        <div>
          <div>
            236
          </div>
          <div>
            重量/g
          </div>
        </div>
        <div>
           <div>
            236
          </div>
          <div>
           果径/cm
          </div>
        </div>
        <div> 
          <div>
            236
          </div>
          <div>
            品质
          </div>
        </div>
      </div>
    </div>
    
    
  </div>
  <div class="four" >
    <div class="fourOne">
      <h1>苹果图片</h1>
      <div>展示苹果在分选过程中的图片和分析结果</div>
    </div>
    <div class="fourTwo">
      <lay-carousel v-model="active1" autoplay style="height:  600px;"> 
    <lay-carousel-item id="1" >
      <div style="color: white;text-align: center;width:100%;height:800px;line-height:600px;background-color:#009688;">条目一</div>
    </lay-carousel-item>
    <lay-carousel-item id="2">
      <div style="color: white;text-align: center;width:100%;height:800px;line-height:600px;background-color:#5FB878;">条目二</div>
    </lay-carousel-item>
    <lay-carousel-item id="3">
      <div style="color: white;text-align: center;width:100%;height:800px;line-height:600px;background-color:#FFB800;">条目三</div>
    </lay-carousel-item>
    <lay-carousel-item id="4">
      <div style="color: white;text-align: center;width:100%;height:800px;line-height:600px;background-color:#FF5722;">条目四</div>
    </lay-carousel-item>
  </lay-carousel>
    </div>
  </div>
  <!-- <div class="bgc"></div> -->
</template>

<script setup >
import { ref } from 'vue'
  const active1 = ref("1")
</script>

<style lang="less" scoped>

.one{
  background:url('../../assets/display/one.jpg') no-repeat;
    width:100%;
    height:100%;
    background-size:100% 100%;
    display: flex;
      justify-content: center;
      align-items: center;
      z-index:999;
    // position:absolute;
    // filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='bg-login.png',sizingMethod='scale')
    .textOne{
    text-align: center;
    color: rgb(74, 58, 2);
       h1{
        font-size: 80px;
        font-weight: 700;
       }
    }
}
.two{
  padding: 40px 80px;
  text-align: center;
  z-index:999;
  // position: relative;
  h1{
        font-size: 50px;
        font-weight: 700;
       }
       video{
        display: inline-block;
        margin-top: 30px;
       }
}
.three{
  text-align: center;
  width: 100%;
  height: 30%;
  // padding: 100px;
    background:url('../../assets/display/one.jpg') no-repeat;
  // background-attachment: local;
  background-size: cover;
  // opacity: 0;
  
    .textThree{
      width: 100%;
   height: 100%;
   display: flex;
   color: #820d0d;
      justify-content: center;
      align-items: center;
      flex-direction: column;
  //  opacity: 1;
   h1{
       font-size: 50px;
       font-weight: 700;
       margin-bottom: 30px;
      }
      .bottom{
        font-size: 20px;
        font-weight: 700;
        width: 100%;
        display: flex;
        padding: 20px;
        justify-content: space-around;
      }
 }   
}
.four{
  display: flex;
   margin-top: 40px;
   justify-content: center;
   align-items: center;
   flex-direction: column;
   padding-bottom: 100px;
   .fourOne{
    text-align: center;
    h1{
    display: block ;
       font-size: 40px;
       font-weight: 700;
       margin-bottom: 10px;
      }
   }
   .fourTwo{
    width: 90%;
    margin-top: 20px;
   
   }

}
// .bgc{
//   // position: fixed;
//   // left: 0;
//   // top: 10vh;
//   background:url('../../assets/display/one.jpg') no-repeat;
//   background-attachment: local;
//   background-size: cover;
//   // z-index: -100;
//   width:100%;
//     height:80%;
// 	  background-position: center;
// }
</style>