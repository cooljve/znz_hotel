import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/manage/Login'
import AddUser from '../components/manage/Register'
import Index from '../components/home/Index'
import AdminIndex from '../components/admin/AdminIndex'
import HomeBody from '../components/admin/HomeBody'
import Setting from '../components/admin/Setting'
import FrontUser from '../components/admin/FrontUserManage'
import DaysReport from '../components/admin/DaysReport'
import Report from '../components/admin/Report'
import ReportSet from '../components/admin/ReportSet'
import Information from '../components/admin/Information'
import RoomManage from '../components/admin/RoomManage/RoomManage'
import Room from '../components/admin/RoomManage/Room'
import RoomCheckIn from '../components/admin/RoomManage/RoomCheckIn'
import Customer from '../components/admin/Order'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '*',
      redirect: '/login'
    },
    {
      path: '/index',
      name: 'Index',
      component: Index
    },
    {
      path: '/manage',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'register',
      component: AddUser
    },
    {
      path: '/frontUser',
      redirect:'/frontUser/room/checkIn',
      name:'frontUser',
      // meta:{
      //   requireLogin:true
      // },
      component: AdminIndex,
      children: [
        {
          path: '/frontUser/room',
          component: Room,
          children:[
            {path: '/frontUser/room/checkIn', component: RoomCheckIn},
          ]
        },
        {path: '/order', component: Customer},
      ]
    },
    {
      path: '/admin',
      redirect:'/admin/home',
      name:'admin',
      // meta:{
      //   requireLogin:true
      // },
      component: AdminIndex,
      children: [
        {path: '/admin/home', component: HomeBody},
        {
          path: '/admin/room',
          component: Room,
          children:[
            {path: '/admin/room/status', component: RoomManage},
            {path: '/admin/room/checkIn', component: RoomCheckIn},
          ]
        },
        {path: '/order', component: Customer},
        {
          path: '/admin/report',
          component: Report,
          children:[
            {path: '/admin/report/days', component: DaysReport},
            {path: '/admin/report/set', component: ReportSet},
          ]
        },
        {
          path: '/admin/setting',
          component: Setting,
          children: [
            {path: '/admin/setting/info', component: Information},
            {path: '/admin/setting/frontUser', component: FrontUser},
          ]
        },
      ]
    }
  ]
})
