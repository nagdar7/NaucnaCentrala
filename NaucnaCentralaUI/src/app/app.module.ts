import { AllTasksComponent } from "./task/all-tasks.component";
import { TaskComponent } from "./task/task.component";
import { Editor } from "./guard/editor.guard";
import { SubmitPaperComponent } from "./paper/submit-paper.component";
import { AllMagazineComponent } from "./magazine/all-magazine.component";
import { NewMagazineComponent } from "./magazine/new-magazine.component";
import { Routing } from "./app.routing";
import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { RouterModule, Routes } from "@angular/router";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { HttpModule, Http, RequestOptions } from "@angular/http";

import { AppComponent } from "./app.component";

import { RegistrationComponent } from "./registration/registration.component";

import { Author } from "./guard/author.guard";
import { Reviewer } from "./guard/reviewer.guard";
import { Authorized } from "./guard/authorized.guard";
import { Notauthorized } from "./guard/notauthorized.guard";
import { LoginComponent } from "./login/login.component";
// import { AuthHttp, AuthConfig } from 'angular2-jwt';

import { HOST_URL } from "../config";
import { JwtInterceptor } from "./helpers/jwt.interceptor";
import { ErrorInterceptor } from "./helpers/error.interceptor";

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    LoginComponent,
    NewMagazineComponent,
    AllMagazineComponent,
    SubmitPaperComponent,
    AllTasksComponent,
    TaskComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(Routing),
    HttpClientModule,
    HttpModule
  ],

  providers: [
    Reviewer,
    Author,
    Editor,
    Authorized,
    Notauthorized,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
    // { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
