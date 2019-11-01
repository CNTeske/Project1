import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule} from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { LoginService } from './services/login.service';
import { HttpClientModule } from '@angular/common/http';
import { MainComponent } from './components/main/main.component';
import { EmployeeComponent } from './components/employee/employee.component';
import { ManagerComponent } from './components/manager/manager.component';
import { ReimbursementsComponent } from './components/reimbursements/reimbursements.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainComponent,
    EmployeeComponent,
    ManagerComponent,
    ReimbursementsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [LoginService],
  bootstrap: [AppComponent]
})
export class AppModule { }
