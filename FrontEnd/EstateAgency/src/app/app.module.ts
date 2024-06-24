import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { IndexComponent } from './components/client/index/index.component';
import { HomeComponent } from './components/client/home/home.component';
import { RoomComponent } from './components/client/room/room.component';
import { AgentComponent } from './components/client/agent/agent.component';
import { AgentDetailComponent } from './components/client/agent-detail/agent-detail.component';
import { RoomRoomdetailComponent } from './components/client/room-roomdetail/room-roomdetail.component';
import { ContactComponent } from './components/client/contact/contact.component';
import {TabViewModule} from 'primeng/tabview';
import {DialogModule} from 'primeng/dialog';
import {ToastModule} from 'primeng/toast';
import {ToolbarModule} from 'primeng/toolbar';
import {CardModule} from 'primeng/card';
import {TableModule} from 'primeng/table';
import {ButtonModule} from 'primeng/button';
import { LoginComponent } from './components/client/login/login.component';
import { DashboardComponent } from './components/manager/dashboard/dashboard.component';
import { ReportadminComponent } from './components/manager/admin/reportadmin/reportadmin.component';
import { AcountadminComponent } from './components/manager/admin/acountadmin/acountadmin.component';
import { ReportagentComponent } from './components/manager/agent/reportagent/reportagent.component';
import { RoomagentComponent } from './components/manager/agent/roomagent/roomagent.component';
import { FormGroup, FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { InformationComponent } from './components/client/information/information.component';
import { MaintenaceComponent } from './components/manager/agent/maintenace/maintenace.component';
import { ContractComponent } from './components/manager/agent/contract/contract.component';
import { RequestComponent } from './components/client/request/request.component';
import { HistoryComponent } from './components/client/history/history.component';
import { FavouriteComponent } from './components/client/favourite/favourite.component';
import { RequestagentComponent } from './components/manager/agent/requestagent/requestagent.component';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { OrderCustommerComponent } from './components/client/order-custommer/order-custommer.component';
import { OrderagentComponent } from './components/manager/agent/orderagent/orderagent.component';
import { ChatComponent } from './components/chat/chat.component';
import { ContactadminComponent } from './components/manager/admin/contactadmin/contactadmin.component';
import { BillComponent } from './components/manager/agent/bill/bill.component';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';

@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    HomeComponent,
    RoomComponent,
    AgentComponent,
    AgentDetailComponent,
    RoomRoomdetailComponent,
    ContactComponent,
    LoginComponent,
    DashboardComponent,
    ReportadminComponent,
    AcountadminComponent,
    ReportagentComponent,
    RoomagentComponent,
    InformationComponent,
    MaintenaceComponent,
    ContractComponent,
    RequestComponent,
    HistoryComponent,
    FavouriteComponent,
    RequestagentComponent,
    OrderCustommerComponent,
    OrderagentComponent,
    ChatComponent,
    ContactadminComponent,
    BillComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    TabViewModule,
    DialogModule,
    ToastModule,
    CardModule,
    ToolbarModule,
    TableModule,
    ButtonModule,
    FormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    NgbModule,
    InputTextModule,
    DropdownModule
  ],
  providers: [
    provideClientHydration()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
