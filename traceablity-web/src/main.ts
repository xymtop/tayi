import { createApp } from 'vue'
import Router from './router'
import Store from './store'
import App from './App.vue'
import { permission } from "./directives/permission";
import './mockjs'
import {Web3} from "web3";
import {web3InitFun} from "./web3-utils/web3-init";
import {checkWallet} from "./web3-utils/wallet-utils";
import {getData, setData} from "./web3-utils/contracts/contracts-api/Traceability";
import "vue-connect-wallet/dist/style.css";
// import VueConnectWallet from "vue-connect-wallet";

// highlightjs
import hljs from 'highlight.js';
import VMdEditor from '@kangc/v-md-editor/lib/codemirror-editor';
import '@kangc/v-md-editor/lib/style/codemirror-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';



// codemirror 编辑器的相关资源
import Codemirror from 'codemirror';
// mode
import 'codemirror/mode/markdown/markdown';
import 'codemirror/mode/javascript/javascript';
import 'codemirror/mode/css/css';
import 'codemirror/mode/htmlmixed/htmlmixed';
import 'codemirror/mode/vue/vue';
// edit
import 'codemirror/addon/edit/closebrackets';
import 'codemirror/addon/edit/closetag';
import 'codemirror/addon/edit/matchbrackets';
// placeholder
import 'codemirror/addon/display/placeholder';
// active-line
import 'codemirror/addon/selection/active-line';
// scrollbar
import 'codemirror/addon/scroll/simplescrollbars';
import 'codemirror/addon/scroll/simplescrollbars.css';
// style
import 'codemirror/lib/codemirror.css';


VMdEditor.Codemirror = Codemirror;
VMdEditor.use(githubTheme, {
    Hljs: hljs,
});

import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
import '@kangc/v-md-editor/lib/theme/style/github.css';



VMdPreview.use(githubTheme, {
    Hljs: hljs,
});
import {graphlib as G6} from "dagre";


import VueImageZoomer from 'vue-image-zoomer';
import AudioPlayer from '@liripeng/vue-audio-player'
import VideoPlayer from 'vue-video-player'

import 'video.js/dist/video-js.css' // 导入video.js的CSS


import contextmenu from "v-contextmenu";
import "v-contextmenu/dist/themes/default.css";


const app = createApp(App)
app.use(Store);
app.use(Router);
app.use(VMdEditor);
app.use(VMdPreview)
app.use(G6)
app.use(VueImageZoomer)
app.use(AudioPlayer)
app.use(VideoPlayer)
app.use(contextmenu);

// app.use(VueConnectWallet)
// app.directive("permission",permission);
//初始化钱包
// const account =await checkWallet()
//
// let data = await getData();
// alert("数据"+data);
// let res = await  setData(1000000)
// let data2 = await getData();
// alert("数据"+data2);

app.mount('#app');
