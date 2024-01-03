<template>
  <div>
    <lay-card v-if="viewType === '文本'">
      <lay-quote>{{ title }}</lay-quote>
      <v-md-preview :text="text"></v-md-preview>
    </lay-card>

    <lay-card v-if="viewType === '视频'">
      <lay-quote>{{ title }}</lay-quote>
      <video-player class="vjs-custom-skin" :options="playerOptions"></video-player>
    </lay-card>

    <lay-card v-if="viewType === '音频'">
      <lay-quote>{{ title }}</lay-quote>
      <audio-player :audio-list="audioSource.map(elm => elm.url)" />
    </lay-card>

    <lay-card v-if="viewType === '图片'">
      <lay-quote>{{ title }}</lay-quote>
      <v-zoomer>
        <img :src="imageUrl" style="object-fit: contain; width: 100%; height: 100%;" />
      </v-zoomer>
    </lay-card>
<!--    地图-->
    <lay-card v-if="viewType === '位置'">
      <lay-quote>{{ title }}</lay-quote>
      <div style="width: 100%; height: 500px;">
        <v-map :center="center" :zoom="zoom" style="width: 100%; height: 100%;">
          < v-tilelayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
          <v-marker :lat-lng="center" />
        </v-map>
      </div>
    </lay-card>

    <lay-card v-if="viewType === '其他'">
      <lay-quote>{{ title }}预览文件已开始下载，请在电脑上打开</lay-quote>
    </lay-card>
  </div>
</template>

<script>
export default {
  name: "DynamicContentViewer",
  props: {
    viewType: String,
    playerOptions: Object,
    audioSource: Array,
    imageUrl: String,
    text: String,
    title: String
  }
};
</script>
