<template>
  <div class="common-layout">
    <el-container>
      <!-- <el-header>Header</el-header> -->
      <el-main>
        <!-- 地图容器 -->
        <div id="map"></div>
      </el-main>
      <el-footer>
        <!-- 非标注视图 -->
        <div style="display: flex; justify-content: center;" v-if="!ifmark">
          <el-form :inline="true" class="demo-form-inline">
            <el-form-item label="Current Zoom">
              {{ currentZoomLevel }}
            </el-form-item>
            <el-form-item label="Mark Mode">
              <el-switch v-model="ifmark" @change="handleSwitchChange"></el-switch>
            </el-form-item>
            <el-form-item>
              <el-button type="danger" @click="formatting">Formatting</el-button>
            </el-form-item>
            <!-- <el-form-item>
              <el-button type="success" @click="handleDataset" class="green-color">Dataset</el-button>
            </el-form-item> -->
            <el-form-item>
              <el-button type="warning" @click="handleCalculation" class="yellow-color">Calculation</el-button>
            </el-form-item>
            <!-- <el-form-item>
              <el-button type="danger" @click="deleteDataset">Delete Dataset</el-button>
            </el-form-item> -->
            <el-form-item>
              <el-button type="warning" @click="handleFilesUpload" class="yellow-color">Files Upload</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="success" @click="handleDatasetLoad" class="green-color">Dataset</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="success" @click="handleTestUpload" class="green-color">TestData Load</el-button>
            </el-form-item>
            <el-form-item label="Chosen Number">
              {{ chosenNumberStr }}
            </el-form-item>
          </el-form>
        </div>
        <!-- 标注视图 -->
        <div style="display: flex; justify-content: center;" v-if="ifmark">
          <el-form :inline="true" class="demo-form-inline">
            <el-form-item label="Sensors">
              {{ sensorsValue }}
            </el-form-item>
            <el-form-item label="Gateways">
              {{ gatewaysValue }}
            </el-form-item>
            <el-form-item label="Mark Mode">
              <el-switch v-model="ifmark" @change="handleSwitchChange"></el-switch>
            </el-form-item>
            <!-- <el-form-item label="NodeType">
              <el-select v-model="nodeType" value-key="id" placeholder="Select" placement="top-start"
                :popper-append-to-body="false" :teleported="false" popper-class="custom-popper"
                :disabled="nodeTypeIsDisabled @change="handleNodeTypeChange">
                <el-option v-for="item in options" :key="item.id" :label="item.label" :value="item" />
              </el-select>
            </el-form-item> -->
            <el-form-item label="NodeType">
              <el-select v-model="nodeType" value-key="id" placeholder="Select" placement="top-start"
                :popper-append-to-body="false" :teleported="false" popper-class="custom-popper"
                :disabled="nodeTypeIsDisabled" @change="handleNodeTypeChange">
                <el-option v-for="item in options" :key="item.id" :label="item.label" :value="item" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="warning" @click="undo" class="yellow-color">Undo</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="danger" @click="formatting">Formatting</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveData">Save Data</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveDataset" :disabled="saveDatasetIsDisabled">Save Dataset</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="success" @click="handleDatasetLoad" class="green-color">Dataset</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-footer>
    </el-container>
    <!-- 计算部分 -->
    <el-dialog title="Calculation" v-model="caculate_dialogVisible" width="30%">
      <div style="display: flex; justify-content: center;">
        <el-tooltip class="box-item" effect="dark" content="Directed greed" placement="top">
          <el-button @click="calLinner">LD</el-button>
        </el-tooltip>
        <el-tooltip class="box-item" effect="dark" content="Genetic algorithm" placement="top">
          <el-button @click="calByGA">GA</el-button>
        </el-tooltip>
        <el-tooltip class="box-item" effect="dark" content="Branch and bound algorithm" placement="top">
          <el-button @click="calByBB">BB</el-button>
        </el-tooltip>
        <el-tooltip class="box-item" effect="dark" content="Plain greedy algorithm" placement="top">
          <el-button @click="calByGD">GD</el-button>
        </el-tooltip>
        <el-tooltip class="box-item" effect="dark" content="Linear Programming Algorithm" placement="top">
          <el-button @click="calByLP">LP</el-button>
        </el-tooltip>
        <el-tooltip class="box-item" effect="dark" content="Ant Colony Algorithm" placement="top">
          <el-button @click="calByAco">ACO</el-button>
        </el-tooltip>
      </div>
    </el-dialog>
    <!-- upload部分 -->
    <el-dialog title="Upload" v-model="upload_dialogVisible" width="30%">
      <div style="display: flex; justify-content: center;">
        <el-button @click="sensorupload">Sensor Upload</el-button>
        <el-button @click="gatewayupload">Gateway Upload</el-button>
        <el-button @click="crossingupload">Crossing Upload</el-button>
      </div>
    </el-dialog>
    <!-- Testdata Upload部分 -->
    <el-dialog title="Which test data would you like to upload?" v-model="TestData_dialogVisible" width="45%">
      <div style="display: flex; justify-content: center;">
        <el-button type="primary" @click="handleLoad('207')">207</el-button>
        <el-button type="primary" @click="handleLoad('461')">461</el-button>
        <el-button type="primary" @click="handleLoad('1052')">1052</el-button>
        <el-button type="primary" @click="handleLoad('1361')">1361</el-button>
        <el-button type="primary" @click="handleLoad('guo114')">G114</el-button>
        <el-button type="primary" @click="handleLoad('guo228')">G228</el-button>
        <el-button type="primary" @click="handleLoad('guo354')">G354</el-button>
        <el-button type="primary" @click="handleLoad('guo484')">G484</el-button>
      </div>
    </el-dialog>
    <!-- Dataset Upload部分 -->
    <el-dialog title="Which dataset would you like to upload?" v-model="Dataset_dialogVisible" width="45%">
      <!-- <div style="display: flex; justify-content: center;">
        <el-tooltip v-for="dataset in setNames" :key="dataset.name" class="box-item" effect="dark" :content="getTooltipContent(dataset)" placement="top">
        <el-button type="primary" @click="handleDatasetButtonClick(dataset.name)">
          {{ dataset.name }}
        </el-button>
      </el-tooltip>
      </div> -->
      <el-table :data="setNames" height="250" style="width: 100%">
        <el-table-column type="index" width="50" />
        <el-table-column label="Name" width="150" prop="name" />
        <el-table-column label="Sensor Size" width="100" prop="sensorSize" />
        <el-table-column label="Gateway Size" width="130" prop="gatewaySize" />
        <el-table-column label="Operations">
          <template #default="scope">
            <el-button size="small" @click="handleLoad(scope.row.name)">Load</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row.name)"
              :disabled="isRestrictedDataset(scope.row.name)">Delete</el-button>
            <el-button size="small" @click="renameDataset(scope.row, scope.$index)"
              :disabled="isRestrictedDataset(scope.row.name)">Rename</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <!-- Dataset 部分 -->
    <el-dialog title="Select the data set operation you want to perform" v-model="AboutDatasetdialogVisible" width="45%">
      <div style="display: flex; justify-content: center;">
        <el-button type="success" @click="handleDatasetLoad" class="green-color">Dataset Load</el-button>
        <el-button type="danger" @click="deleteDataset">Delete Dataset</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
//@ts-ignore
import axios from 'axios';
// import { onScopeDispose } from 'vue';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import { fromLonLat, toLonLat } from 'ol/proj';
import { Icon, Style } from 'ol/style';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import VectorSource from 'ol/source/Vector';
import VectorLayer from 'ol/layer/Vector';
import { defaults as defaultControls, ScaleLine } from 'ol/control';
import { defaults as defaultInteractions } from 'ol/interaction';
import { Coordinate } from 'ol/coordinate';
import Polygon from 'ol/geom/Polygon';
import { Fill, Stroke } from 'ol/style';
import { Circle } from 'ol/geom';
import { getDistance } from 'ol/sphere';
import MouseWheelZoom from 'ol/interaction/MouseWheelZoom';

let map: any;
const createMap = () => {
  map = new Map({
    target: 'map', // 地图容器的DOM id
    interactions: defaultInteractions({
      doubleClickZoom: false,
      mouseWheelZoom: false, // 禁用滚轮缩放
    }),
    layers: [
      new TileLayer({
        source: new OSM(), // 使用OpenStreetMap作为地图源
      }),
    ],
    view: new View({
      // center: fromLonLat([112.8835, 22.9082]), // 将经纬度转换为地图坐标
      center: fromLonLat([112.86756332262867, 22.914367512190353]), // 将经纬度转换为地图坐标
      zoom: 18,
      minZoom: 10, // 设置缩放的最小值
      maxZoom: 22, // 设置缩放的最大值
    }),
    controls: defaultControls().extend([
      new ScaleLine(), // 添加比例尺控件
    ]),
  });

  // 监听地图的moveend事件来更新缩放级别
  map.on('moveend', () => {
    currentZoomLevel.value = map.getView().getZoom();
  });

  // 初始化缩放级别
  currentZoomLevel.value = map.getView().getZoom();
}


// 添加自定义图标
const addIcon = (iconSrc: string, size: number[], position: Coordinate, zIndex = 1, anchorvalue = { x: 0.5, y: 0.5 }) => {
  const iconFeature = new Feature({
    geometry: new Point(fromLonLat(position)), // 图标位置
  });

  iconFeature.setStyle(new Style({
    image: new Icon({
      anchor: [anchorvalue.x, anchorvalue.y], // 图标锚点
      src: iconSrc,
      size: size,
      scale: [1, 1], // 图标缩放比例
    }),
  }));

  const vectorSource = new VectorSource({
    features: [iconFeature],
  });

  const vectorLayer = new VectorLayer({
    source: vectorSource,
    zIndex: zIndex // 使用函数参数设置自定义图标图层的zIndex
  });

  map.addLayer(vectorLayer);
};


// 使用自定义图标
const gIconSrc = "./assets/icon/jian.png";
const sIconSrc = "./assets/icon/point16.png";
const cIconSrc = "./assets/icon/green.png";


// 自定义变量
// 使用 ref 声明响应式数据
const all_sensor = ref<any[][]>([]);    // 存放地图上所有的传感器节点
const all_gateway = ref<any[][]>([]);   // 存放地图上所有的网关节点
const chosenNumber = ref(0); // 初始值为整数 0
const currentZoomLevel = ref(18); // 初始值为


let cross: any[] = [];         //存放地图上所有的交叉路口
let pointArr: any[] = [];      //存放一条路的起点与终点
let pts: any[] = [];           //存放一条路所有的传感器节点或者网关节点
let pointIndex = 0;     //初始化节点之间的距离

const ifmark = ref(false);
const caculate_dialogVisible = ref(false);
const upload_dialogVisible = ref(false);
const TestData_dialogVisible = ref(false);
const Dataset_dialogVisible = ref(false);
const AboutDatasetdialogVisible = ref(false);

// 存放数据集信息
const setNames = ref<Array<{ name: string; sensorSize: number; gatewaySize: number }>>([]);
const nodeTypeIsDisabled = computed(() => !ifmark.value);
const saveDatasetIsDisabled = computed(() => !ifmark.value);
//const saveDataIsDisabled = computed(() => !ifmark.value);

// 计算所有传感器节点的个数并累加
const sensorsValue = computed(() => {
  let totalSensors = 0;
  for (const row of all_sensor.value) {
    totalSensors += row.length;
  }
  return totalSensors;
});

// 计算所有传感器节点的个数并累加
const chosenNumberStr = computed(() => {
  if (chosenNumber.value === 0) {
    return "_ _"
  }
  else {
    return chosenNumber.value
  }
});

// 计算所有网关节点的个数并累加
const gatewaysValue = computed(() => {
  let totalGateways = 0;
  for (const row of all_gateway.value) {
    totalGateways += row.length;
  }
  return totalGateways;
});

const isRestrictedDataset = (name: string) => {
  const restrictedDatasets = ['1052', '1361', '207', '461', 'guo114', 'guo228', 'guo354', 'guo484'];
  return restrictedDatasets.includes(name);
};


type Option = {
  id: number
  label: string
  desc: string
}
const nodeType = ref<Option>()
nodeType.value = { id: 1, label: 'sensor', desc: '路段传感器标记' }
const options = ref([
  { id: 1, label: 'sensor', desc: '路段传感器标记' },
  { id: 2, label: 'gateway', desc: '路段网关标记' },
  { id: 3, label: 'sensor_single', desc: '单点传感器标记' },
  { id: 4, label: 'gateway_single', desc: '单点网关标记' },
  { id: 5, label: 'cross', desc: '路口标记' },
  { id: 6, label: 'None', desc: '无标记' },
])

// 清除所有矢量图层
function clearVectorLayers() {
  map.getLayers().getArray()
    .filter((layer: any) => layer instanceof VectorLayer)
    .forEach((layer: any) => map.removeLayer(layer));
}


const handleLoad = (name: string) => {
  // 在这里处理按钮点击逻辑
  axios.post('datasetLoad', null, {
    params: {
      datasetName: name
    }
  })
    .then((response) => {
      //清空所有存放数据的数组
      all_sensor.value = [];
      all_gateway.value = [];
      chosenNumber.value = 0;
      cross = [];
      pointArr = [];
      pts = [];
      // 清除地图上已经标记的点
      // map.clearOverlays();
      clearVectorLayers();

      let location_lng = response.data.data[0]
      let location_lat = response.data.data[1]

      const view = map.getView();
      const newCenter = fromLonLat([location_lng, location_lat]);
      view.setCenter(newCenter);
      view.setZoom(18);
      // var data_point = new BMap.Point(location_lng, location_lat);// 标注位置
      // map.centerAndZoom(data_point, 19);
      sensorupload();
      Dataset_dialogVisible.value = false;
    })
    .catch(function (error: any) {
      ElMessage({
        message: error,
        type: 'error',
      })
    });
}

const handleDelete = (index: number, name: string) => {
  ElMessageBox.confirm(
    'Are you sure you want to delete the dataset?',
    'Warning',
    {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      type: 'warning',
      center: true,
      lockScroll: false
    }
  )
    .then(() => {
      const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(255, 255, 255, 0.5)',
      })
      // 发送网络请求
      axios.post('deleteDataset', null, {
        params: {
          datasetName: name
        }
      })
        .then(() => {
          if (index >= 0 && index < setNames.value.length) {
            setNames.value.splice(index, 1);
          }
          loading.close();
          ElMessage({
            message: 'Dataset deleted successfully',
            type: 'success',
          })
        })
        .catch((error: { response: { data: { message: any; }; }; }) => {
          loading.close();
          const mess = error.response.data.message;
          if (mess != null)
            ElMessage.error(mess)
          else
            ElMessage.error('请求失败')
        });
      // ElMessage({
      //   type: 'success',
      //   message: 'Delete completed',
      // })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: 'Delete canceled',
      })
    })
}

// 处理撤销
let guard_undo: string;
const undo = () => {
  if (guard_undo === "sensor") {
    all_sensor.value.pop();
  }
  if (guard_undo === "cross") {
    cross.pop();
  }
  if (guard_undo === "gateway") {
    all_gateway.value.pop();
  }
  if (guard_undo === "pointArr_first") {
    pointArr.pop();
  }
  drawAllMarked();
};


// function handleDatasetButtonClick(name: string) {
//   // 在这里处理按钮点击逻辑
//   axios.post('datasetLoad', null, {
//     params: {
//       datasetName: name
//     }
//   })
//     .then((response) => {
//       let location_lng = response.data.data[0]
//       let location_lat = response.data.data[1]
//       // 清除地图上已经标记的点
//       map.clearOverlays();
//       var data_point = new BMap.Point(location_lng, location_lat);// lyj标注位置
//       map.centerAndZoom(data_point, 19);
//       sensorupload();
//     })
//     .catch(function (error: any) {
//       ElMessage({
//         message: error,
//         type: 'error',
//       })
//     });
//   Dataset_dialogVisible.value = false;
//   AboutDatasetdialogVisible.value = false;
// }

const handleSwitchChange = () => {
  if (ifmark.value) {
    // 当 ifmark 为真（开关打开）时执行的代码
    map.getTargetElement().style.cursor = 'crosshair'; // 设置地图光标为十字光标
    map.on('singleclick', handleClick); // 添加单击事件监听器

    // 添加鼠标滚轮缩放交互
    const mouseWheelZoom = new MouseWheelZoom();
    map.addInteraction(mouseWheelZoom);

  } else {
    // 当 ifmark 为假（开关关闭）时执行的代码
    map.getTargetElement().style.cursor = ''; // 重置地图光标为默认样式
    map.un('singleclick', handleClick); // 移除单击事件监听器

    // 移除鼠标滚轮缩放交互
    const interactionsToRemove: MouseWheelZoom[] = [];
    map.getInteractions().forEach((interaction: any) => {
      if (interaction instanceof MouseWheelZoom) {
        interactionsToRemove.push(interaction);
      }
    });
    interactionsToRemove.forEach(interaction => map.removeInteraction(interaction));

    // 重置缩放级别
    map.getView().setZoom(18);
  }
};

function handleNodeTypeChange() {
  var the_nodetype = nodeType.value?.label;
  if (the_nodetype === "None") {
    // 开关关闭时执行的代码
    map.getTargetElement().style.cursor = "url(https://api.map.baidu.com/images/openhand.cur) 8 8, default";
  } else {
    // 其他情况，如 "Node" 类型被选中
    map.getTargetElement().style.cursor = "crosshair";
  }
}

function handleClick(e: {
  coordinate: any;
}) {
  var coordinate = e.coordinate;

  // 如果需要获取经纬度，可以将坐标转换回经纬度
  var lonLat = toLonLat(coordinate);
  // 打印点击位置经纬度信息
  console.log(lonLat);
  var the_nodetype = nodeType.value?.label
  if (the_nodetype === "None") {
    map.getTargetElement().style.cursor = "url(https://api.map.baidu.com/images/openhand.cur) 8 8, default";
  }
  else if (the_nodetype === "cross") {
    cross.push(lonLat);
    guard_undo = 'cross';
    const iconSize = [32, 32]; // 根据您的图标实际尺寸调整
    // 使用 addIcon 函数添加交叉点标记
    addIcon(cIconSrc, iconSize, lonLat);
    //绘制所有标注节点
    drawAllMarked();
  } else if (the_nodetype === "sensor_single") {
    let tmp: any = []
    tmp.push(lonLat)
    all_sensor.value.push(tmp)
    guard_undo = 'sensor';
    const iconSize = [16, 16]; // 根据您的图标实际尺寸调整
    // 使用 addIcon 函数添加交叉点标记
    addIcon(sIconSrc, iconSize, lonLat);
    //绘制所有标注节点
    drawAllMarked();
  } else if (the_nodetype === "gateway_single") {
    let tmp2: any = []
    tmp2.push(lonLat)
    all_gateway.value.push(tmp2)
    guard_undo = 'gateway';

    const iconSize = [32, 32]; // 根据图标实际尺寸调整
    // 使用 addIcon 函数添加交叉点标记
    addIcon(gIconSrc, iconSize, lonLat, 2);

    //绘制所有标注节点
    drawAllMarked();
  } else {
    pointArr.push(lonLat);         // 存放起点与终点
    if (pointArr.length === 1) {
      const iconSize = [32, 32]; // 根据您的图标实际尺寸调整
      addIcon(cIconSrc, iconSize, pointArr[0]);
      guard_undo = 'pointArr_first';
    }
    if (pointArr.length === 2) {

      var start = pointArr[0];
      // map.addOverlay(new BMap.Marker(start));
      // pts.push(start);   //有bug
      var end = pointArr[1];

      const iconSize = [32, 32]; // 根据您的图标实际尺寸调整
      addIcon(cIconSrc, iconSize, end);

      // pts.push(end);
      pointArr = []

      var x1 = start[0];
      var y1 = start[1];
      var x2 = end[0];
      var y2 = end[1];

      if (the_nodetype == "sensor") {     // 如果标注的是sensor节点，则点距为6
        pointIndex = 1;
      } else {
        pointIndex = 20;
      }
      //利用相似三角形求出等距离的点的坐标
      // var chang = map.getDistance(start, end).toFixed(2);
      var chang = getDistance(start, end);
      for (var i = pointIndex; i < chang; i += pointIndex) {
        var x3 = i / chang * (x1 - x2) + x2;
        // x3 = x3.toFixed(6);
        var y3 = i / chang * (y1 - y2) + y2;
        // y3 = y3.toFixed(6);
        const pointxy = [x3, y3];
        pts.push(pointxy);
      }
      //二维数组，存放多条街道的坐标点
      if (the_nodetype == "sensor") {
        all_sensor.value.push(pts)
        guard_undo = 'sensor';
        pts = []
        // console.log(all_sensor[0][0].lng, all_sensor[0][0].lat);
      } else {
        all_gateway.value.push(pts)
        guard_undo = 'gateway';
        pts = []
        // console.log(all_sensor[0][0].lng, all_sensor[0][0].lat);
      }
      // all.push(pts)
      // 延迟 250 毫秒后执行 drawAllMarked()
      setTimeout(function () {
        //绘制所有标注节点
        drawAllMarked();
      }, 250);
    }
  }
}

// 创建多边形并添加到地图的函数
function createAndAddPolygon(vertex_points: any[]) {
  var transformedVertexPoints = vertex_points.map(function (point) {
    return fromLonLat([point[0], point[1]]);
  });

  // 创建多边形几何对象
  var polygonGeometry = new Polygon([transformedVertexPoints]);

  // 创建样式
  var polygonStyle = new Style({
    stroke: new Stroke({
      color: '#ECD118', // 设置线条颜色
      width: 3 // 设置线条宽度
    })
  });

  // 创建要素并设置样式
  var polygonFeature = new Feature(polygonGeometry);
  polygonFeature.setStyle(polygonStyle);

  // 创建矢量源（如果尚未创建）
  const vectorSource = new VectorSource();

  // 将要素添加到矢量源
  vectorSource.addFeature(polygonFeature);

  // 创建矢量图层（如果尚未创建并添加到地图）
  const vectorLayer = new VectorLayer({
    source: vectorSource,
    zIndex: 1 // 设置自定义图标图层的zIndex，确保其大于多边形图层的zIndex
  });

  // 假设有一个地图实例`map`，将矢量图层添加到地图上
  map.addLayer(vectorLayer);
}


//绘制所有标注节点
function drawAllMarked() {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(255, 255, 255, 0.5)',
  })
  chosenNumber.value = 0;
  // 删除已经标注的点
  clearVectorLayers();
  // 在地图上标注路口节点
  for (let j = 0; j < cross.length; j++) {
    // 图标的尺寸
    const iconSize = [32, 32]; // 根据您的图标实际尺寸调整
    // 使用 addIcon 函数添加交叉点标记
    addIcon(cIconSrc, iconSize, cross[j]);
  }
  // 在地图上标注网关节点
  for (let j = 0; j < all_gateway.value.length; j++) {
    for (let k = 0; k < all_gateway.value[j].length; k++) {
      // 定义图标尺寸和偏移量
      const iconSize = [32, 32]; // 根据您的图标实际尺寸调整
      const anchor = { x: 0.5, y: 1 }; // 调整锚点位置

      // 使用 addIcon 函数添加标记
      addIcon(gIconSrc, iconSize, all_gateway.value[j][k], 2, anchor);
    }
  }
  // 在地图上标注传感器节点
  for (var j = 0; j < all_sensor.value.length; j++) {
    if (all_sensor.value[j].length === 1) {
      // 根据图标实际尺寸进行调整
      const iconSize = [16, 16]; // 假定尺寸，实际可能需要调整

      // 使用 addIcon 函数添加传感器图标
      addIcon(sIconSrc, iconSize, all_sensor.value[j][0]);
      // markerSsessor.addEventListener('click', (e) => {
      //   let lat = e.currentTarget.latLng.lat
      //   let lng = e.currentTarget.latLng.lng
      //   alert(lat + '  ' + lng);
      // });
    } else if (all_sensor.value[j].length !== 0) {
      // var xx1 = all_sensor.value[j][0][0];
      // var yy1 = all_sensor.value[j][0][1];
      // var xx2 = all_sensor.value[j][all_sensor.value[j].length - 1][0];
      // var yy2 = all_sensor.value[j][all_sensor.value[j].length - 1][1];
      // var rad = Math.atan2((yy2 - yy1), (xx2 - xx1))  //弧度  0.6435011087932844
      // var theta = rad * (180 / Math.PI);  //角度  36.86989764584402
      // var width = 0.000044
      // var height = 0.00002
      // var half_width = width / 2
      // var half_height = height / 2
      // var distance = Math.sqrt(Math.pow(half_width, 2) + Math.pow(half_height, 2))
      // var the_alpha = Math.atan2(half_height, half_width)

      for (var k = 0; k < all_sensor.value[j].length; k++) {
        var centerPoint = [all_sensor.value[j][k][0], all_sensor.value[j][k][1]];

        // var vertex_points: any[] = []; // 用于存储多边形顶点的数组

        // var vertex_1 = [centerPoint[0] + distance * Math.cos(rad + the_alpha), centerPoint[1] + distance * Math.sin(rad + the_alpha)]
        // vertex_points.push(vertex_1);

        // var vertex_2 = [centerPoint[0] + distance * Math.cos(rad - the_alpha), centerPoint[1] + distance * Math.sin(rad - the_alpha)];
        // vertex_points.push(vertex_2);

        // var vertex_3 = [centerPoint[0] + distance * Math.cos(rad + the_alpha + Math.PI), centerPoint[1] + distance * Math.sin(rad + the_alpha + Math.PI)];
        // vertex_points.push(vertex_3);

        // var vertex_4 = [centerPoint[0] + distance * Math.cos(rad - the_alpha + Math.PI), centerPoint[1] + distance * Math.sin(rad - the_alpha + Math.PI)];
        // vertex_points.push(vertex_4);

        // var vertex_5 = vertex_1;
        // vertex_points.push(vertex_5);

        // createAndAddPolygon(vertex_points);


        const iconSize = [16, 16]; // 假定尺寸，实际可能需要调整
        // 使用 addIcon 函数添加传感器图标
        addIcon(sIconSrc, iconSize, centerPoint);

        // 车框
        // var marker0 = new GL.Marker(point1, {
        //     icon: rIcon
        // });
        // marker0.setRotation(theta)
        // map.addOverlay(marker0)
        // 车中心红点、表示传感器
        // 使用api绘制红点
        // var circle_marker1 = new GL.Circle(point1, 0.1, {
        //     strokeColor: 'red',
        //     strokeWeight: 2,
        //     strokeOpacity: 0.1, //圆形的边线透明度
        //     strokeStyle: 'solid',
        // });
        // circle_marker1.setFillColor('#D81E06')
        // map.addOverlay(circle_marker1);

        // 使用图标绘制红点
        // var marker1 = new BMap.Marker(point1, {
        //   icon: sIcon
        // });
        // map.addOverlay(marker1)
      }
    }
  }
  loading.close();
}

const formatting = () => {
  ElMessageBox.confirm(
    'All points marked on the map will be cleared，Continue?',
    'Warning',
    {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      type: 'warning',
      center: true,
      lockScroll: false
    }
  )
    .then(() => {
      //清空所有存放数据的数组
      all_sensor.value = [];
      all_gateway.value = [];
      chosenNumber.value = 0;
      cross = [];
      pointArr = [];
      pts = [];
      // 清除地图上已经标记的点
      // map.clearOverlays();
      clearVectorLayers();
      ElMessage({
        type: 'success',
        message: 'Delete completed',
      })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: 'Delete canceled',
      })
    })
};


const saveData = () => {
  // 交叉路口节点数据
  var cross_points: any = [];
  for (var i = 0; i < cross.length; i++) {
    cross_points.push(cross[i]);
  }
  // sensor节点数据
  var sensor_array: any = [];
  for (var i = 0; i < all_sensor.value.length; i++) {
    let sensor_points = [];
    for (var j = 0; j < all_sensor.value[i].length; j++) {
      // let a: any = [];
      // a.push(all_sensor.value[i][j][0]);
      // a.push(all_sensor.value[i][j][1]);
      // sensor_points.push(a);
      sensor_points.push(all_sensor.value[i][j])

    }
    sensor_array.push(sensor_points);
  }
  // 网关节点数据
  var gateway_array: any = [];
  for (var i = 0; i < all_gateway.value.length; i++) {
    let gateway_points = [];
    for (var j = 0; j < all_gateway.value[i].length; j++) {
      // let a: any = [];
      // a.push(all_gateway.value[i][j][0]);
      // a.push(all_gateway.value[i][j][1]);
      // gateway_points.push(a);
      gateway_points.push(all_gateway.value[i][j]);
    }
    gateway_array.push(gateway_points);
  }
  const quickSaveData = {
    "cross_points": cross_points,
    "gateway_array": gateway_array,
    "sensor_array": sensor_array,
    // "roadName": roadName,
  };

  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(255, 255, 255, 0.5)',
  })
  axios.post('hhquicksaveNew', quickSaveData)
    .then((_response: { data: any; }) => {
      loading.close();
      // 请求成功时的处理
      // console.log(response.data);
      ElMessage({
        message: 'Success!',
        type: 'success',
      });
      pointArr = [];
    })
    .catch((error: any) => {
      loading.close();
      // 请求失败时的处理
      console.error(error);
      ElMessage({
        message: error,
        type: 'error',
      });
    });
};

const deleteDataset = () => {
  ElMessageBox.prompt('Please enter the name of the dataset you want to delete', 'Tip', {
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
    inputPattern: /^[a-zA-Z0-9]{1,8}$/,
    inputErrorMessage: 'Input values contain only letters and numbers and are no more than 8 characters long',
  })
    .then(({ value }) => {
      const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(255, 255, 255, 0.5)',
      })
      // 发送网络请求
      axios.post('deleteDataset', null, {
        params: {
          datasetName: value
        }
      })
        .then(() => {
          loading.close();
          ElMessage({
            message: 'Dataset deleted successfully',
            type: 'success',
          })
        })
        .catch((error: { response: { data: { message: any; }; }; }) => {
          loading.close();
          const mess = error.response.data.message;
          if (mess != null)
            ElMessage.error(mess)
          else
            ElMessage.error('请求失败')
        });
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: 'Input canceled',
      })
    })
}

// const handleDataset = () => {
//   AboutDatasetdialogVisible.value = true;
// }

const saveDataset = () => {
  ElMessageBox.prompt('Please set dataset name', 'Tip', {
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
    inputPattern: /^[a-zA-Z0-9]{1,8}$/,
    inputErrorMessage: 'Input values contain only letters and numbers and are no more than 8 characters long',
  })
    .then(({ value }) => {
      const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(255, 255, 255, 0.5)',
      })
      let current_location: any = [];
      // 获取地图视图中心点的坐标
      let center = map.getView().getCenter();
      // 将中心点坐标从Web Mercator转换为经纬度
      current_location = toLonLat(center);

      // 构建请求体数据
      const requestData = {
        datasetName: value,
        current_location: current_location,
      };

      // 发送网络请求
      axios.post('saveDataset', requestData)
        .then(() => {
          loading.close();
          ElMessage({
            message: 'Dataset saved successfully',
            type: 'success',
          })
        })
        .catch((error: { response: { data: { message: any; }; }; }) => {
          loading.close();
          const mess = error.response.data.message;
          if (mess != null)
            ElMessage.error(mess)
          else
            ElMessage.error('请求失败')
        });
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: 'Input canceled',
      })
    })
}

const renameDataset = (row: any, rowIndex: number) => {
  // 定义标题字符串
  const title = `Rename Dataset: ${row.name}`;
  ElMessageBox.prompt('Please enter the new dataset name', title, {
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
    inputPattern: /^[a-zA-Z0-9]{1,8}$/,
    inputErrorMessage: 'Name must be 1-8 characters long, letters and numbers only',
  })
    .then(({ value }) => {
      const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(255, 255, 255, 0.5)',
      });

      // 构建请求体数据
      const requestData = {
        oldName: row.name,
        newName: value,
      };

      // 发送网络请求进行重命名
      axios.post('renameDataset', requestData)
        .then(() => {
          loading.close();
          ElMessage({
            message: 'Dataset renamed successfully',
            type: 'success',
          });

          // 更新数据集列表
          if (rowIndex >= 0 && rowIndex < setNames.value.length) {
            setNames.value[rowIndex].name = value;
          }
        })
        .catch((error: { response: { data: { message: any; }; }; }) => {
          loading.close();
          const mess = error.response.data.message;
          if (mess != null)
            ElMessage.error(mess)
          else
            ElMessage.error('Rename failed')
        });
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: 'Rename canceled',
      });
    });
}

// 添加圆形
const addCircle = (position: Coordinate, radius: number | undefined, strokeColor: any, fillColor: any, strokeWidth: any) => {
  // 创建圆形几何对象，半径单位为米
  const circleGeometry = new Circle(fromLonLat(position), radius);

  // 创建特征并设置其几何形状为圆形
  const circleFeature = new Feature(circleGeometry);

  // 定义样式
  const circleStyle = new Style({
    stroke: new Stroke({
      color: strokeColor,
      width: strokeWidth,
    }),
    fill: new Fill({
      color: fillColor,
    }),
  });

  // 应用样式
  circleFeature.setStyle(circleStyle);

  // 创建矢量源并添加特征
  const vectorSource = new VectorSource({
    features: [circleFeature],
  });

  // 创建矢量图层
  const vectorLayer = new VectorLayer({
    source: vectorSource,
  });

  // 将矢量图层添加到地图
  map.addLayer(vectorLayer);
};


function drawPoints(response: { data: { gatewayList: any; sensorList: any; }; }) {
  var dataGateway = response.data.gatewayList;//response.data计算出来的最终网关坐标
  chosenNumber.value = dataGateway[0].length;
  alert(dataGateway[0].length + " gateways have been used! ")
  //在地图上标记网关坐标
  for (var j = 0; j < dataGateway[0].length; j++) {
    var temp = dataGateway[0][j].split(",");
    const position = [temp[0], temp[1]]; // 经度和纬度

    var radius = 72; // 半径为68米
    var strokeColor = 'blue'; // 边框颜色
    var fillColor = 'rgba(255, 255, 255, 0.4)'; // 填充颜色
    var strokeWidth = 1; // 边框宽度
    addCircle(position, radius, strokeColor, fillColor, strokeWidth);
    // 绘制圆
    // var colors = ['blue','red','green','black','blue'];

    // var circle = new BMap.Circle(point1, 68, {
    //   strokeColor: 'blue',
    //   strokeWeight: 2,
    //   strokeOpacity: 0.4, //圆形的边线透明度
    //   // strokeStyle: 'solid'
    //   strokeStyle: 'dashed',  // 边线虚线样式
    //   fillOpacity: 0.7    //填充透明度
    // });
    // map.addOverlay(circle);

    const offset = { x: 0.5, y: 1 }; // X偏移和Y偏移
    addIcon(gIconSrc, [32, 32], position, 2, offset);
  }

  // 标记传感器节点
  var sensors = response.data.sensorList;//response.data计算出来的最终网关坐标
  for (var i = 0; i < sensors.length; i++) {
    const longitude = sensors[i][3];
    const latitude = sensors[i][4];
    const position = [longitude, latitude]; // OpenLayers 需要先经度后纬度的顺序
    // 假设 sIcon 是您的传感器图标路径，size 是图标的尺寸，例如 [20, 20]
    const iconSize = [16, 16]; // 根据您的图标实际尺寸调整
    // 使用 addIcon 函数添加传感器标记
    addIcon(sIconSrc, iconSize, position);
  }
}

const handleCalculation = () => {
  caculate_dialogVisible.value = true;
};

const handleFilesUpload = () => {
  upload_dialogVisible.value = true;
};

const handleTestUpload = () => {
  TestData_dialogVisible.value = true;
};

const handleDatasetLoad = () => {
  // 发送网络请求
  axios.post('searchDatasetInfo')
    .then((response: { data: { data: Array<any> } }) => {
      const datasetInfoList = response.data.data;
      const setNamesData = datasetInfoList.map((datasetInfo: any) => {
        return {
          name: datasetInfo.name,
          sensorSize: datasetInfo.sensorSize,
          gatewaySize: datasetInfo.gatewaySize
        };
      });

      // 更新 setNames 的值
      setNames.value = setNamesData;
      Dataset_dialogVisible.value = true;
    })
    .catch((error: { response: { data: { message: any } } }) => {
      const mess = error.response.data.message;
      if (mess != null)
        ElMessage.error(mess);
      else
        ElMessage.error('请求失败');
    });
};

const calByGA = () => {
  // 清除地图上已经标记的点
  clearVectorLayers();
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(255, 255, 255, 0.5)',
  })
  // 发送网络请求
  axios.post('calByGA', null, {
    params: {
      crosFlag: 'withCros'
    }
  })
    .then((response: { data: any; }) => {
      drawPoints(response.data);
      loading.close();
      ElMessage({
        message: '网关绘制完成',
        type: 'success',
      })
      caculate_dialogVisible.value = false;
    })
    .catch((error: { response: { data: { message: any; }; }; }) => {
      loading.close();
      caculate_dialogVisible.value = false;
      const mess = error.response.data.message;
      if (mess != null)
        ElMessage.error(mess)
      else
        ElMessage.error('请求失败')
    });
};

const calLinner = () => {
  // 清除地图上已经标记的点
  clearVectorLayers();
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(255, 255, 255, 0.5)',
  })
  // 发送网络请求
  axios.post('hhln', null, {
    params: {
      crosFlag: 'withCros'
    }
  })
    .then((response: { data: any; }) => {
      drawPoints(response.data);
      loading.close();
      ElMessage({
        message: '网关绘制完成',
        type: 'success',
      })
      caculate_dialogVisible.value = false;
    })
    .catch((error: { response: { data: { message: any; }; }; }) => {
      loading.close();
      caculate_dialogVisible.value = false;
      const mess = error.response.data.message;
      if (mess != null)
        ElMessage.error(mess)
      else
        ElMessage.error('请求失败')
    });
};

const calByAco = () => {
  // 清除地图上已经标记的点
  clearVectorLayers();
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(255, 255, 255, 0.5)',
  })
  // 发送网络请求
  axios.post('hhaco', null, {
    params: {
      crosFlag: 'withCros'
    }
  })
    .then((response: { data: any; }) => {
      drawPoints(response.data);
      loading.close();
      ElMessage({
        message: '网关绘制完成',
        type: 'success',
      })
      caculate_dialogVisible.value = false;
    })
    .catch((error: { response: { data: { message: any; }; }; }) => {
      loading.close();
      caculate_dialogVisible.value = false;
      const mess = error.response.data.message;
      if (mess != null)
        ElMessage.error(mess)
      else
        ElMessage.error('请求失败')
    });
};

const calByLP = () => {
  // 清除地图上已经标记的点
  clearVectorLayers();
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(255, 255, 255, 0.5)',
  })
  // 发送网络请求
  axios.post('hhLP', null, {
    params: {
      crosFlag: 'withCros'
    }
  })
    .then((response: { data: any; }) => {
      drawPoints(response.data);
      loading.close();
      ElMessage({
        message: '网关绘制完成',
        type: 'success',
      })
      caculate_dialogVisible.value = false;
    })
    .catch((error: { response: { data: { message: any; }; }; }) => {
      loading.close();
      caculate_dialogVisible.value = false;
      const mess = error.response.data.message;
      if (mess != null)
        ElMessage.error(mess)
      else
        ElMessage.error('请求失败')
    });
};

const calByGD = () => {
  // 清除地图上已经标记的点
  clearVectorLayers();
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(255, 255, 255, 0.5)',
  })
  // 发送网络请求
  axios.post('hhe', null, {
    params: {
      crosFlag: 'withCros'
    }
  })
    .then((response: { data: any; }) => {
      drawPoints(response.data);
      loading.close();
      ElMessage({
        message: '网关绘制完成',
        type: 'success',
      })
      caculate_dialogVisible.value = false;
    })
    .catch((error: { response: { data: { message: any; }; }; }) => {
      loading.close();
      caculate_dialogVisible.value = false;
      const mess = error.response.data.message;
      if (mess != null)
        ElMessage.error(mess)
      else
        ElMessage.error('请求失败')
    });
};

const calByBB = () => {
  // 清除地图上已经标记的点
  // map.clearOverlays();
  clearVectorLayers();
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(255, 255, 255, 0.5)',
  })
  // 发送网络请求
  axios.post('hhbb', null, {
    params: {
      crosFlag: 'withCros'
    }
  })
    .then((response: { data: any; }) => {
      drawPoints(response.data);
      loading.close();
      ElMessage({
        message: '网关绘制完成',
        type: 'success',
      })
      caculate_dialogVisible.value = false;
    })
    .catch((error: { response: { data: { message: any; }; }; }) => {
      loading.close();
      caculate_dialogVisible.value = false;
      const mess = error.response.data.message;
      if (mess != null)
        ElMessage.error(mess)
      else
        ElMessage.error('请求失败')
    });
};


const crossingupload = () => {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(255, 255, 255, 0.5)',
  })
  axios.post('cup')
    .then((response: { data: { data: any; }; }) => {
      var crossing = response.data.data; // 拿到的cros列表
      cross = [];
      // 在地图上标记
      for (var i = 0; i < crossing.length; i++) {
        // 提取交叉点的经度和纬度
        const longitude = crossing[i][2];
        const latitude = crossing[i][3];
        // 构建位置数组，OpenLayers 需要先经度后纬度的顺序
        const position = [longitude, latitude];

        // 存储处理过的交叉点位置数据（可选）
        let tmp = [];
        tmp.push(position);
        cross.push(tmp);

        // 使用 addIcon 函数为地图上的每个交叉点位置添加自定义图标
        // 假设 cIconSrc 是您为交叉点定义的图标路径
        addIcon(cIconSrc, [32, 32], position);
      }
      loading.close();
      ElMessage({
        message: '路口绘制完成',
        type: 'success',
      })
    })
    .catch((error: any) => {
      loading.close();
      console.error(error);
      ElMessage({
        message: error,
        type: 'error',
      })
    });
};

const gatewayupload = () => {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(255, 255, 255, 0.5)',
  })
  axios.post('gup')
    .then((response: { data: { data: any; }; }) => {
      var gateways = response.data.data; // 拿到的gateway列表
      all_gateway.value = [];
      // 在地图上标记
      for (var i = 0; i < gateways.length; i++) {
        // var point2 = new BMap.Point(gateways[i][3], gateways[i][4]);
        // 提取每个传感器的经度和纬度
        const longitude = gateways[i][3];
        const latitude = gateways[i][4];
        // 构建位置数组，注意 OpenLayers 需要先经度后纬度的顺序
        const position = [longitude, latitude];
        let tmp = [];
        // tmp.push(point2);
        tmp.push(position);
        all_gateway.value.push(tmp);
        // var marker2 = new BMap.Marker(point2, { icon: gIcon_rec32 });
        // var marker2 = new BMap.Marker(point2, { icon: gIcon });
        // var marker2 = new GL.Marker(point2, {icon: gIcon_rec16});
        // map.addOverlay(marker2);
        addIcon(gIconSrc, [32, 32], position, 2);
        // marker2.setTop(true);
      }
      loading.close();
      ElMessage({
        message: '网关绘制完成',
        type: 'success',
      })
    })
    .catch((error: any) => {
      loading.close();
      ElMessage({
        message: error,
        type: 'error',
      })
    });
};

const sensorupload = () => {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(255, 255, 255, 0.5)',
  });
  axios.post('sup')
    .then((response: { data: { data: any; }; }) => {
      var sensors = response.data.data; // 拿到的sensor列表
      all_sensor.value = [];
      // 在地图上标记
      for (var i = 0; i < sensors.length; i++) {
        // var point1 = new BMap.Point(sensors[i][3], sensors[i][4]);
        // 提取每个传感器的经度和纬度
        const longitude = sensors[i][3];
        const latitude = sensors[i][4];
        // 构建位置数组，注意 OpenLayers 需要先经度后纬度的顺序
        const position = [longitude, latitude];
        let tmp = [];
        // tmp.push(point1);
        tmp.push(position);
        all_sensor.value.push(tmp);
        addIcon(sIconSrc, [16, 16], position);
        // var marker1 = new BMap.Marker(point1, { icon: sIcon });
        // map.addOverlay(marker1);
      }
      setTimeout(() => {
        loading.close();
        ElMessage({
          message: '传感器节点绘制完成',
          type: 'success',
        });
      }, 1000); // 使用 setTimeout 延迟 0 毫秒
    })
    .catch((error: any) => {
      loading.close();
      ElMessage({
        message: error,
        type: 'error',
      });
    });
};

onMounted(() => {
  createMap();
});

</script>
 
<style scoped>
#map {
  width: 100%;
  /* height: 1000px; */
  height: 85vh;
}

.yellow-color {
  background-color: #FFB800;
  /* 设置自定义的背景颜色 */
  /* color: #FFFFFF; 设置自定义的文本颜色 */
}

.green-color:hover {
  background-color: #33ABA0;
  /* 设置自定义的背景颜色 */
  /* color: #FFFFFF; 设置自定义的文本颜色 */
}

.green-color {
  background-color: #009688;
  /* 设置自定义的背景颜色 */
  /* color: #FFFFFF; 设置自定义的文本颜色 */
}
</style>