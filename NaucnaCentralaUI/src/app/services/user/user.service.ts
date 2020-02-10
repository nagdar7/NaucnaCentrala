import { Injectable } from "@angular/core";

import { Headers, RequestOptions, ResponseContentType } from "@angular/http";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Http, Response } from "@angular/http";

import { Observable } from "rxjs";

import { HOST_URL } from "../../../config";
import { map } from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class UserService {
  categories = [];
  languages = [];
  books = [];

  constructor(private httpClient: HttpClient, private http: Http) {}

  startProcess(name) {
    return this.httpClient.get(
      HOST_URL + "/api/v1/user/".concat(name)
    ) as Observable<any>;
  }

  registerUser(user, taskId) {
    return this.httpClient.post(
      HOST_URL + "/api/v1/user/registration/".concat(taskId),
      user
    ) as Observable<any>;
  }

  loginUser(user, taskId) {
    return this.httpClient
      .post(HOST_URL + "/api/v1/user/login/".concat(taskId), user)
      .pipe(
        map((res: any) => {
          localStorage.setItem("jwt-token", res.value);
          const user = this.parseJwt(res.value);
          localStorage.setItem("user", user);
          localStorage.setItem("roles", user.roles);
          return user;
        })
      ) as Observable<any>;
  }

  logout() {
    localStorage.removeItem("jwt-token");
    localStorage.removeItem("user");
    localStorage.removeItem("roles");
  }

  // getTasks(processInstance : string){

  //   return this.httpClient.get(HOST_URL+'/welcome/get/tasks/'.concat(processInstance)) as Observable<any>
  // }

  // claimTask(taskId){
  //   return this.httpClient.post(HOST_URL+'/welcome/tasks/claim/'.concat(taskId), null) as Observable<any>
  // }

  // completeTask(taskId){
  //   return this.httpClient.post(HOST_URL+'/welcome/tasks/complete/'.concat(taskId), null) as Observable<any>
  // }

  private parseJwt(token) {
    var base64Url = token.split(".")[1];
    var base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    var jsonPayload = decodeURIComponent(
      atob(base64)
        .split("")
        .map(function(c) {
          return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
        })
        .join("")
    );

    return JSON.parse(jsonPayload);
  }
}
