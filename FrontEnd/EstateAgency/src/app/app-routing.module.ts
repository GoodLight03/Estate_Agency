import { OrderagentComponent } from './components/manager/agent/orderagent/orderagent.component';
import { OrderCustommerComponent } from './components/client/order-custommer/order-custommer.component';
import { HomeComponent } from './components/client/home/home.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { IndexComponent } from './components/client/index/index.component';
import { RoomComponent } from './components/client/room/room.component';
import { RoomRoomdetailComponent } from './components/client/room-roomdetail/room-roomdetail.component';
import { AgentComponent } from './components/client/agent/agent.component';
import { AgentDetailComponent } from './components/client/agent-detail/agent-detail.component';
import { ContactComponent } from './components/client/contact/contact.component';
import { LoginComponent } from './components/client/login/login.component';
import { DashboardComponent } from './components/manager/dashboard/dashboard.component';
import { ReportadminComponent } from './components/manager/admin/reportadmin/reportadmin.component';
import { ReportagentComponent } from './components/manager/agent/reportagent/reportagent.component';
import { AcountadminComponent } from './components/manager/admin/acountadmin/acountadmin.component';
import { RoomagentComponent } from './components/manager/agent/roomagent/roomagent.component';
import { InformationComponent } from './components/client/information/information.component';
import { MaintenaceComponent } from './components/manager/agent/maintenace/maintenace.component';
import { ContractComponent } from './components/manager/agent/contract/contract.component';
import { RequestComponent } from './components/client/request/request.component';
import { HistoryComponent } from './components/client/history/history.component';
import { FavouriteComponent } from './components/client/favourite/favourite.component';
import { RequestagentComponent } from './components/manager/agent/requestagent/requestagent.component';
import { RoleGuardService } from './service/role-guard.service';
import { ChatComponent } from './components/chat/chat.component';
import { ContactadminComponent } from './components/manager/admin/contactadmin/contactadmin.component';
import { BillComponent } from './components/manager/agent/bill/bill.component';
import { ContractcustomerComponent } from './components/client/contractcustomer/contractcustomer.component';
import { ChatsocketComponent } from './components/chatsocket/chatsocket.component';
import { ChatfinalComponent } from './components/chatfinal/chatfinal.component';

const routes: Routes = [
  {
    path: '', component: IndexComponent,
    children: [
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
      },
      {
        path: 'home', component: HomeComponent,
      },
      {
        path: 'room', component: RoomComponent,
      },
      {
        path: 'roomdetail/:id', component: RoomRoomdetailComponent,
      },
      {
        path: 'agent', component: AgentComponent,
      },
      {
        path: 'agentdetail/:id', component: AgentDetailComponent,
      },
      {
        path: 'contact', component: ContactComponent,
      },
      {
        path: 'information', component: InformationComponent,
      },
      {
        path: 'history', component: HistoryComponent,
      },
      {
        path: 'request', component: RequestComponent,
      },
      {
        path: 'favourite', component: FavouriteComponent,
      },
      {
        path: 'order', component: OrderCustommerComponent,
      },
      {
        path: 'chat', component: ChatfinalComponent,canActivate: [RoleGuardService], data: { expectedRole: "ROLE_CUSTOMER" }
      },
      {
        path: 'bill/:id', component: BillComponent,
      },
      {
        path: 'contract/:id', component: ContractcustomerComponent,
      },
    ]
  },
  {
    path: 'admin', component: DashboardComponent, canActivate: [RoleGuardService], data: { expectedRole: "ROLE_ADMIN" },
    children: [
      {
        path: 'report', component: ReportadminComponent,
      },
      {
        path: 'acount', component: AcountadminComponent,
      },
      {
        path: 'contact', component: ContactadminComponent,
      }
    ]
  },
  {
    path: 'agent', component: DashboardComponent, canActivate: [RoleGuardService], data: { expectedRole: "ROLE_AGENT" },
    children: [
      {
        path: 'report', component: ReportagentComponent,
      },
      {
        path: 'room', component: RoomagentComponent,
      },
      {
        path: 'maintenace', component: MaintenaceComponent,
      },
      {
        path: 'contract', component: ContractComponent,
      },
      {
        path: 'bill/:id', component: BillComponent,
      },
      {
        path: 'order/:id', component: OrderagentComponent,
      },
      {
        path: 'chat', component: ChatfinalComponent
      },
      {
        path: 'request/:id', component: RequestagentComponent,
      },
      {
        path: 'information', component: InformationComponent,
      }
    ]
  },
  { path: 'login', component: LoginComponent },
  // {path: 'chatsocket/:idRoomChat', component: ChatsocketComponent},
  // {path: 'chatfinal', component: ChatfinalComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
