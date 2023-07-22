<template>
  <div class="common-layout">
    <el-container>
      <!-- <el-header>Header</el-header> -->
      <el-main>
        <!-- 百度地图容器 -->
        <div id="map"></div>
      </el-main>
      <el-footer>
        <!-- <div style="display: flex; justify-content: center;">
          <el-form :inline="true" class="demo-form-inline">
            <el-form-item label="Mark Mode">
              <el-switch v-model="ifmark" @change="handleSwitchChange"></el-switch>
            </el-form-item>
            <el-form-item label="NodeType">
              <el-select v-model="nodeType" value-key="id" placeholder="Select" placement="top-start"
                :popper-append-to-body="false" :teleported="false" popper-class="custom-popper"
                :disabled="nodeTypeIsDisabled">
                <el-option v-for="item in options" :key="item.id" :label="item.label" :value="item" />
              </el-select>
            </el-form-item>
          </el-form>
          <el-button type="primary" @click="saveData" :disabled="saveDataIsDisabled">Save Data</el-button>
          <el-button type="success" @click="handleDataset" class="green-color">Dataset</el-button>
          <el-button type="success" @click="formatting" class="green-color">Formatting</el-button>
          <el-button type="warning" @click="handleCalculation" class="yellow-color">Calculation</el-button>
          <el-button type="warning" @click="handleFilesUpload" class="yellow-color">Files Upload</el-button>
          <el-button type="success" @click="handleTestUpload" class="green-color">TestData Load</el-button>
        </div> -->
        <div style="display: flex; justify-content: center;">
          <el-form :inline="true" class="demo-form-inline">
            <el-form-item label="Mark Mode">
              <el-switch v-model="ifmark" @change="handleSwitchChange"></el-switch>
            </el-form-item>
            <el-form-item label="NodeType">
              <el-select v-model="nodeType" value-key="id" placeholder="Select" placement="top-start"
                :popper-append-to-body="false" :teleported="false" popper-class="custom-popper"
                :disabled="nodeTypeIsDisabled">
                <el-option v-for="item in options" :key="item.id" :label="item.label" :value="item" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveData" :disabled="saveDataIsDisabled">Save Data</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="success" @click="handleDataset" class="green-color">Dataset</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="warning" @click="handleCalculation" class="yellow-color">Calculation</el-button>
            </el-form-item>
            <el-form-item>
              <!-- <el-button type="success" @click="formatting" class="green-color">Formatting</el-button> -->
              <el-button type="danger" @click="formatting">Formatting</el-button>
            </el-form-item>
            <!-- <el-form-item>
            <el-button type="success" @click="onSubmit" class="green-color">Clear Database</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="onSubmit" class="green-color">Sort</el-button>
          </el-form-item> -->
            <el-form-item>
              <el-button type="warning" @click="handleFilesUpload" class="yellow-color">Files Upload</el-button>
            </el-form-item>
            <el-form-item>
              <el-button type="success" @click="handleTestUpload" class="green-color">TestData Load</el-button>
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
        <el-tooltip class="box-item" effect="dark" content="Sensor Upload" placement="top">
          <el-button @click="sensorupload">Sensor Upload</el-button>
        </el-tooltip>
        <el-tooltip class="box-item" effect="dark" content="Gateway Upload" placement="top">
          <el-button @click="gatewayupload">Gateway Upload</el-button>
        </el-tooltip>
        <el-tooltip class="box-item" effect="dark" content="Crossing Upload" placement="top">
          <el-button @click="crossingupload">Crossing Upload</el-button>
        </el-tooltip>
      </div>
    </el-dialog>
    <!-- Testdata Upload部分 -->
    <el-dialog title="Which test data would you like to upload?" v-model="TestData_dialogVisible" width="45%">
      <div style="display: flex; justify-content: center;">
        <el-button type="primary" @click="tdata207">207</el-button>
        <el-button type="primary" @click="tdata461">461</el-button>
        <el-button type="primary" @click="tdata1052">1052</el-button>
        <el-button type="primary" @click="tdata1361">1361</el-button>
        <el-button type="primary" @click="tdatag114">G114</el-button>
        <el-button type="primary" @click="tdatag228">G228</el-button>
        <el-button type="primary" @click="tdatag354">G354</el-button>
        <el-button type="primary" @click="tdatag484">G484</el-button>
      </div>
    </el-dialog>
    <!-- Dataset Upload部分 -->
    <el-dialog title="Which dataset would you like to upload?" v-model="Dataset_dialogVisible" width="45%">
      <div style="display: flex; justify-content: center;">
        <el-button v-for="name in setNames" :key="name" type="primary" @click="handleDatasetButtonClick(name)">{{ name
        }}</el-button>
      </div>
    </el-dialog>
    <!-- Dataset 部分 -->
    <el-dialog title="Select the data set operation you want to perform" v-model="AboutDatasetdialogVisible" width="45%">
      <div style="display: flex; justify-content: center;">
        <el-button type="primary" @click="saveDataset" :disabled="saveDatasetIsDisabled">Save Dataset</el-button>
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

let BMap: any;
let map: any;
let gIcon: any;
let gIcon_rec32: any;
// let gIcon_rec16: any;
let sIcon: any;
// let rIcon: any;
let cIcon: any;

// 自定义变量
let all_sensor: any[][] = [];    //存放地图上所有的传感器节点
let all_gateway: any[][] = [];   //存放地图上所有的网关节点
let cross: any[] = [];         //存放地图上所有的交叉路口
let pointArr: any[] = [];      //存放一条路的起点与终点
let pts: any[] = [];           //存放一条路所有的传感器节点或者网关节点
// let str = [];
let pointIndex = 0;     //初始化节点之间的距离

const ifmark = ref(false);
const caculate_dialogVisible = ref(false);
const upload_dialogVisible = ref(false);
const TestData_dialogVisible = ref(false);
const Dataset_dialogVisible = ref(false);
const AboutDatasetdialogVisible = ref(false);
const setNames = ref<string[]>([]);
const nodeTypeIsDisabled = computed(() => !ifmark.value);
const saveDataIsDisabled = computed(() => !ifmark.value);
const saveDatasetIsDisabled = computed(() => !ifmark.value);

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
])

const createMap = () => {
  BMap = window.BMap;
  map = new BMap.Map("map");
  const point = new BMap.Point(112.8835, 22.9082);
  map.centerAndZoom(point, 19);
  map.enableScrollWheelZoom(true);
  // map.disableScrollWheelZoom(true);
  map.disableDoubleClickZoom(true);
  map.addControl(new BMap.ScaleControl());
  const defaultMapType = map.getMapType();
  const defaultTileLayer = defaultMapType.getTileLayer();
  const newMapType = new BMap.MapType('新地图', defaultTileLayer, { minZoom: 18, maxZoom: 21 });
  map.setMapType(newMapType);
  // var zoomCtrl = new BMap.ZoomControl(); //添加缩放控件
  // map.addControl(zoomCtrl);
  map.setMapStyleV2({
    styleJson: [
      {
        "featureType": "building",
        "elementType": "all",
        "stylers": {
          "visibility": "off"
        }
      },
      {
        "featureType": "poilabel",
        "elementType": "all",
        "stylers": {
          "visibility": "off"
        }
      },
      {
        "featureType": "manmade",
        "elementType": "all",
        "stylers": {
          "visibility": "off"
        }
      },
    ]
  });
  // 创建自定义网关图标
  gIcon = new BMap.Icon("./assets/icon/jian.png", new BMap.Size(32, 32));
  gIcon_rec32 = new BMap.Icon("./assets/icon/rectangle32.png", new BMap.Size(32, 32));
  // gIcon_rec16 = new BMap.Icon("./assets/icon/rectangle16.png", new BMap.Size(16, 16));
  // 传感器图标
  sIcon = new BMap.Icon("./assets/icon/point16.png", new BMap.Size(16, 16));
  // 车位图标
  // rIcon = new BMap.Icon("./assets/icon/parkingspace.png", new BMap.Size(48, 48));
  // 路口图标
  cIcon = new BMap.Icon("./assets/icon/green.png", new BMap.Size(32, 32));

};

function handleDatasetButtonClick(name: string) {
  // 在这里处理按钮点击逻辑
  axios.post('datasetLoad', null, {
    params: {
      datasetName: name
    }
  })
    .then((response) => {
      let location_lng = response.data.data[0]
      let location_lat = response.data.data[1]
      // 清除地图上已经标记的点
      map.clearOverlays();
      var data_point = new BMap.Point(location_lng, location_lat);// lyj标注位置
      map.centerAndZoom(data_point, 19);
      sensorupload();
    })
    .catch(function (error: any) {
      ElMessage({
        message: error,
        type: 'error',
      })
    });
  Dataset_dialogVisible.value = false;
  AboutDatasetdialogVisible.value = false;
}

const handleSwitchChange = () => {
  if (ifmark.value) {
    // 开关打开时执行的代码
    map.setDefaultCursor("crosshair");
    map.addEventListener('click', handleClick);
  } else {
    // 开关关闭时执行的代码
    map.setDefaultCursor("url(https://api.map.baidu.com/images/openhand.cur) 8 8,default");
    map.removeEventListener('click', handleClick);
  }
};

function handleClick(e: { point: any; }) {
  var the_nodetype = nodeType.value?.label
  if (the_nodetype === "cross") {
    cross.push(e.point);
    var markerCros = new BMap.Marker(e.point, {
      icon: cIcon
    });
    map.addOverlay(markerCros);
  } else if (the_nodetype === "sensor_single") {
    let tmp = []
    tmp.push(e.point)
    all_sensor.push(tmp)
    var markerSingleSessor = new BMap.Marker(e.point, {
      icon: sIcon
    });
    map.addOverlay(markerSingleSessor);
  } else if (the_nodetype === "gateway_single") {
    let tmp2 = []
    tmp2.push(e.point)
    all_gateway.push(tmp2)
    var markerSingleGateway = new BMap.Marker(e.point, {
      icon: gIcon
    });
    let offset = new BMap.Size(0, -15)       // 向上移，使图标点对准指定点
    markerSingleGateway.setOffset(offset)
    map.addOverlay(markerSingleGateway);
  } else {
    pointArr.push(e.point);         // 存放起点与终点
    if (pointArr.length === 1) {
      var start = new BMap.Point(pointArr[0].lng, pointArr[0].lat);
      map.addOverlay(new BMap.Marker(start));
    }
    if (pointArr.length === 2) {
      var start = new BMap.Point(pointArr[0].lng, pointArr[0].lat);
      // map.addOverlay(new BMap.Marker(start));
      // pts.push(start);   //有bug
      var end = new BMap.Point(pointArr[1].lng, pointArr[1].lat);
      // map.addOverlay(new BMap.Marker(end));
      // pts.push(end);
      pointArr = []

      var x1 = start.lng;
      var y1 = start.lat;
      var x2 = end.lng;
      var y2 = end.lat;

      if (the_nodetype == "sensor") {     // 如果标注的是sensor节点，则点距为5
        pointIndex = 5;
      } else {
        pointIndex = 15;
      }
      //利用相似三角形求出等距离的点的坐标
      // var chang = map.getDistance(start, end).toFixed(2);
      var chang = map.getDistance(start, end);
      for (var i = pointIndex; i < chang; i += pointIndex) {
        var x3 = i / chang * (x1 - x2) + x2;
        // x3 = x3.toFixed(6);
        var y3 = i / chang * (y1 - y2) + y2;
        // y3 = y3.toFixed(6);
        var point = new BMap.Point(x3, y3);
        pts.push(point);
      }
      //二维数组，存放多条街道的坐标点
      if (the_nodetype == "sensor") {
        all_sensor.push(pts)
        pts = []
        // console.log(all_sensor[0][0].lng, all_sensor[0][0].lat);
      } else {
        all_gateway.push(pts)
        pts = []
        // console.log(all_sensor[0][0].lng, all_sensor[0][0].lat);
      }
      // all.push(pts)
      //绘制所有标注节点
      drawAllMarked();
    }
  }
}

//绘制所有标注节点
function drawAllMarked() {
  // 绘制所有节点
  // 删除已经标注的点
  map.clearOverlays()
  // 在地图上标注路口节点
  for (let j = 0; j < cross.length; j++) {
    let cross_point = new BMap.Point(cross[j].lng, cross[j].lat);
    marker1 = new BMap.Marker(cross_point, {
      icon: cIcon
    })
    map.addOverlay(marker1)
  }
  // 在地图上标注网关节点
  for (let j = 0; j < all_gateway.length; j++) {
    for (let k = 0; k < all_gateway[j].length; k++) {
      let point1 = new BMap.Point(all_gateway[j][k].lng, all_gateway[j][k].lat);
      marker1 = new BMap.Marker(point1, {
        icon: gIcon
      })
      // var offset = new BMap.Size(-10,-10) // 向左向上移
      var offset = new BMap.Size(0, -15)       // 向上移，使图标点对准指定点
      marker1.setOffset(offset)
      // console.log(marker1.getOffset())
      map.addOverlay(marker1)
    }
  }
  // 在地图上标注传感器节点
  for (var j = 0; j < all_sensor.length; j++) {
    if (all_sensor[j].length === 1) {
      let the_point = new BMap.Point(all_sensor[j][0].lng, all_sensor[j][0].lat);
      let markerSsessor = new BMap.Marker(the_point, {
        icon: sIcon
      });
      map.addOverlay(markerSsessor)
    } else if (all_sensor[j].length !== 0) {
      var xx1 = all_sensor[j][0].lng;
      var yy1 = all_sensor[j][0].lat;
      var xx2 = all_sensor[j][all_sensor[j].length - 1].lng;
      var yy2 = all_sensor[j][all_sensor[j].length - 1].lat;
      var rad = Math.atan2((yy2 - yy1), (xx2 - xx1))  //弧度  0.6435011087932844
      // var theta = rad * (180 / Math.PI);  //角度  36.86989764584402
      var width = 0.000044
      var height = 0.00002
      var half_width = width / 2
      var half_height = height / 2
      var distance = Math.sqrt(Math.pow(half_width, 2) + Math.pow(half_height, 2))
      var the_alpha = Math.atan2(half_height, half_width)

      for (var k = 0; k < all_sensor[j].length; k++) {
        var point1 = new BMap.Point(all_sensor[j][k].lng, all_sensor[j][k].lat);
        // 绘制车框
        var ros_point = point1
        var vertex_points = [];

        var vertex_1 = new BMap.Point(ros_point.lng + distance * Math.cos(rad + the_alpha), ros_point.lat + distance * Math.sin(rad + the_alpha));
        vertex_points.push(vertex_1);

        var vertex_2 = new BMap.Point(ros_point.lng + distance * Math.cos(rad - the_alpha), ros_point.lat + distance * Math.sin(rad - the_alpha))
        vertex_points.push(vertex_2);

        var vertex_3 = new BMap.Point(ros_point.lng + distance * Math.cos(rad + the_alpha + Math.PI), ros_point.lat + distance * Math.sin(rad + the_alpha + Math.PI))
        vertex_points.push(vertex_3);

        var vertex_4 = new BMap.Point(ros_point.lng + distance * Math.cos(rad - the_alpha + Math.PI), ros_point.lat + distance * Math.sin(rad - the_alpha + Math.PI))
        vertex_points.push(vertex_4);

        var vertex_5 = vertex_1;
        vertex_points.push(vertex_5);

        var poly_test = new BMap.Polyline(vertex_points);
        poly_test.setStrokeColor('#ECD118');
        poly_test.setStrokeWeight(3);
        map.addOverlay(poly_test);
        // 车框
        // var marker0 = new BMap.Marker(point1, {
        //     icon: rIcon
        // });
        // marker0.setRotation(theta)
        // map.addOverlay(marker0)
        // 车中心红点、表示传感器
        // 使用api绘制红点
        // var circle_marker1 = new BMap.Circle(point1, 0.1, {
        //     strokeColor: 'red',
        //     strokeWeight: 2,
        //     strokeOpacity: 0.1, //圆形的边线透明度
        //     strokeStyle: 'solid',
        // });
        // circle_marker1.setFillColor('#D81E06')
        // map.addOverlay(circle_marker1);
        // 使用图标绘制红点
        var marker1 = new BMap.Marker(point1, {
          icon: sIcon
        });
        map.addOverlay(marker1)
      }
    }
  }
}

// const onSubmit = () => {
//   console.log('submit!');
// };

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
      all_sensor = [];
      all_gateway = [];
      cross = [];
      pointArr = [];
      pts = [];
      // 清除地图上已经标记的点
      map.clearOverlays();
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
  ifmark.value = !ifmark.value;
  // 交叉路口节点数据
  var cross_points = [];
  for (var i = 0; i < cross.length; i++) {
    var a = [];
    a.push(cross[i].lng);
    a.push(cross[i].lat);
    cross_points.push(a);
  }
  // sensor节点数据
  var sensor_array = [];
  for (var i = 0; i < all_sensor.length; i++) {
    let sensor_points = [];
    for (var j = 0; j < all_sensor[i].length; j++) {
      let a = [];
      a.push(all_sensor[i][j].lng);
      a.push(all_sensor[i][j].lat);
      sensor_points.push(a);
    }
    sensor_array.push(sensor_points);
  }
  // 网关节点数据
  var gateway_array = [];
  for (var i = 0; i < all_gateway.length; i++) {
    let gateway_points = [];
    for (var j = 0; j < all_gateway[i].length; j++) {
      let a = [];
      a.push(all_gateway[i][j].lng);
      a.push(all_gateway[i][j].lat);
      gateway_points.push(a);
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
    .then(() => {
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
      // console.error(error);
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

const handleDataset = () => {
  AboutDatasetdialogVisible.value = true;
}

const saveDataset = () => {
  ifmark.value = !ifmark.value;
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
      let current_location = [];
      current_location.push(map.getCenter().lng);
      current_location.push(map.getCenter().lat);
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

function drawPoints(response: { data: { gatewayList: any; sensorList: any; }; }) {
  var dataGateway = response.data.gatewayList;//response.data计算出来的最终网关坐标
  alert(dataGateway[0].length + " gateways have been used! ")
  //在地图上标记网关坐标
  for (var j = 0; j < dataGateway[0].length; j++) {
    var temp = dataGateway[0][j].split(",");
    var point1 = new BMap.Point(temp[0], temp[1]);
    var marker1 = new BMap.Marker(point1, {
      icon: gIcon
    });
    let offset = new BMap.Size(0, -15)       // 向上移，使图标点对准指定点
    marker1.setOffset(offset)
    map.addOverlay(marker1);
    marker1.setTop(true);
    // 绘制圆
    // var colors = ['blue','red','green','black','blue'];
    var circle = new BMap.Circle(point1, 68, {
      strokeColor: 'blue',
      strokeWeight: 2,
      strokeOpacity: 0.4, //圆形的边线透明度
      // strokeStyle: 'solid'
      strokeStyle: 'dashed',  // 边线虚线样式
      fillOpacity: 0.5    //填充透明度
    });
    map.addOverlay(circle);
  }

  // 标记传感器节点
  var sensors = response.data.sensorList;//response.data计算出来的最终网关坐标
  for (var i = 0; i < sensors.length; i++) {
    var sensor_point = new BMap.Point(sensors[i][3], sensors[i][4]);
    var sensor_marker = new BMap.Marker(sensor_point, { icon: sIcon });
    // var marker0 = new BMap.Marker(point1, {icon: rIcon});
    // map.addOverlay(marker0);
    map.addOverlay(sensor_marker);
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
  axios.post('searchSetnames')
    .then((response: { data: any; }) => {
      setNames.value = response.data.data;
      Dataset_dialogVisible.value = true;
    })
    .catch((error: { response: { data: { message: any; }; }; }) => {
      const mess = error.response.data.message;
      if (mess != null)
        ElMessage.error(mess)
      else
        ElMessage.error('请求失败')
    });
  Dataset_dialogVisible.value = true;
};

const calByGA = () => {
  // 清除地图上已经标记的点
  map.clearOverlays();
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
  map.clearOverlays();
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
  map.clearOverlays();
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
  map.clearOverlays();
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
  map.clearOverlays();
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
  map.clearOverlays();
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
        var point3 = new BMap.Point(crossing[i][2], crossing[i][3]);
        let tmp = [];
        tmp.push(point3);
        cross.push(tmp);
        var marker3 = new BMap.Marker(point3, { icon: cIcon });
        map.addOverlay(marker3);
      }
      loading.close();
      ElMessage({
        message: '路口绘制完成',
        type: 'success',
      })
    })
    .catch((error: any) => {
      loading.close();
      // console.error(error);
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
      all_gateway = [];
      // 在地图上标记
      for (var i = 0; i < gateways.length; i++) {
        var point2 = new BMap.Point(gateways[i][3], gateways[i][4]);
        let tmp = [];
        tmp.push(point2);
        all_gateway.push(tmp);
        // var marker2 = new BMap.Marker(point2, {icon: gIcon});
        var marker2 = new BMap.Marker(point2, { icon: gIcon_rec32 });
        // var marker2 = new BMap.Marker(point2, {icon: gIcon_rec16});
        map.addOverlay(marker2);
        marker2.setTop(true);
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
  })
  axios.post('sup')
    .then((response: { data: { data: any; }; }) => {
      var sensors = response.data.data; // 拿到的sensor列表
      all_sensor = [];
      // 在地图上标记
      for (var i = 0; i < sensors.length; i++) {
        var point1 = new BMap.Point(sensors[i][3], sensors[i][4]);
        let tmp = [];
        tmp.push(point1);
        all_sensor.push(tmp);
        var marker1 = new BMap.Marker(point1, { icon: sIcon });
        map.addOverlay(marker1);
      }
      loading.close();
      ElMessage({
        message: '传感器节点绘制完成',
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



onMounted(() => {
  createMap();
});



const tdata207 = () => {
  axios.post("hh207")
    .then(function () {
      // 清除地图上已经标记的点
      map.clearOverlays();
      var data_point = new BMap.Point(113.80634855083106, 22.816818813420948);// lyj标注位置
      map.centerAndZoom(data_point, 19);
      sensorupload();
    })
    .catch(function (error: any) {
      ElMessage({
        message: error,
        type: 'error',
      })
    });
};

const tdata461 = () => {
  axios.post("hh461")
    .then(function () {
      // 清除地图上已经标记的点
      map.clearOverlays();
      // layer.msg("The data is loaded in the database successfully!");
      var data_point = new BMap.Point(113.80612846598119, 22.817647453549856);
      map.centerAndZoom(data_point, 19);
      sensorupload();
    })
    .catch(function (error: any) {
      ElMessage({
        message: error,
        type: 'error',
      })
    });
};

const tdata1052 = () => {
  axios.post("hh1052")
    .then(function () {
      // 清除地图上已经标记的点
      map.clearOverlays();
      // layer.msg("The data is loaded in the database successfully!");
      var data_point = new BMap.Point(113.8062048219495, 22.816673072167394);
      map.centerAndZoom(data_point, 19);
      sensorupload();
    })
    .catch(function (error: any) {
      ElMessage({
        message: error,
        type: 'error',
      })
    });
};

const tdata1361 = () => {
  axios.post("hh1361")
    .then(function () {
      // 清除地图上已经标记的点
      map.clearOverlays();
      // layer.msg("The data is loaded in the database successfully!");
      var data_point = new BMap.Point(113.80726931397848, 22.816231683408517);
      map.centerAndZoom(data_point, 19);
      sensorupload();
    })
    .catch(function (error: any) {
      ElMessage({
        message: error,
        type: 'error',
      })
    });
};

const tdatag114 = () => {
  axios.post("hhg114")
    .then(function () {
      // 清除地图上已经标记的点
      map.clearOverlays();
      // layer.msg("The data is loaded in the database successfully!");
      var data_point = new BMap.Point(112.8835, 22.9082);
      map.centerAndZoom(data_point, 19);
      sensorupload();
    })
    .catch(function (error: any) {
      ElMessage({
        message: error,
        type: 'error',
      })
    });
};

const tdatag228 = () => {
  axios.post("hhg228")
    .then(function () {
      // 清除地图上已经标记的点
      map.clearOverlays();
      // layer.msg("The data is loaded in the database successfully!");
      var data_point = new BMap.Point(112.8835, 22.9082);
      map.centerAndZoom(data_point, 19);
      sensorupload();
    })
    .catch(function (error: any) {
      ElMessage({
        message: error,
        type: 'error',
      })
    });
};

const tdatag354 = () => {
  axios.post("hhg354")
    .then(function () {
      // 清除地图上已经标记的点
      map.clearOverlays();
      // layer.msg("The data is loaded in the database successfully!");
      var data_point = new BMap.Point(112.8835, 22.9082);
      map.centerAndZoom(data_point, 19);
      sensorupload();
    })
    .catch(function (error: any) {
      ElMessage({
        message: error,
        type: 'error',
      })
    });
};

const tdatag484 = () => {
  axios.post("hhg484")
    .then(function () {
      // 清除地图上已经标记的点
      map.clearOverlays();
      // layer.msg("The data is loaded in the database successfully!");
      var data_point = new BMap.Point(112.8835, 22.9082);
      map.centerAndZoom(data_point, 19);
      sensorupload();
    })
    .catch(function (error: any) {
      ElMessage({
        message: error,
        type: 'error',
      })
    });
};
</script>
 
<style scoped>
#map {
  width: 100%;
  /* height: 1000px; */
  height: 85vh;
  /* 设置高度为视口高度的 90% */
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