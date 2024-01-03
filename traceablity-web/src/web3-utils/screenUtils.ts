
//获取整个屏幕的中心点
export function getScreenCenter() {
  return {
    x: window.innerWidth / 2,
    y: window.innerHeight / 2,
  };
}

//获取某个dom容器的中心点
export function getDomCenter(dom: HTMLElement) {
  return {
    x: dom.offsetLeft + dom.offsetWidth / 2,
    y: dom.offsetTop + dom.offsetHeight / 2,
  };
}