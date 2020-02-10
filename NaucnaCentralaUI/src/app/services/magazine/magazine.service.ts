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
export class MagazineService {
  categories = [];
  languages = [];
  books = [];

  constructor(private httpClient: HttpClient, private http: Http) {}

  startProcess() {
    return this.httpClient.get(HOST_URL + "/api/v1/magazine") as Observable<
      any
    >;
  }

  createNewMagazine(magazine, taskId) {
    return this.httpClient.post(
      HOST_URL + "/api/v1/magazine/".concat(taskId),
      magazine
    ) as Observable<any>;
  }

  asignRolesToMagazine(roles, taskId) {
    return this.httpClient.post(
      HOST_URL + "/api/v1/magazine/roles/".concat(taskId),
      roles
    ) as Observable<any>;
  }

  findAll() {
    return this.httpClient.get(HOST_URL + "/api/v1/magazine/all") as Observable<
      any
    >;
  }

  // loginUser(user, taskId) {
  //   return this.httpClient
  //     .post(HOST_URL + "/api/v1/user/login/".concat(taskId), user)
  //     .pipe(
  //       map((res: any) => {
  //         localStorage.setItem("jwt-token", res.value);
  //         const user = this.parseJwt(res.value);
  //         localStorage.setItem("user", user);
  //         localStorage.setItem("roles", user.roles);
  //         return user;
  //       })
  //     ) as Observable<any>;
  // }

  // logout() {
  //   localStorage.removeItem("jwt-token");
  //   localStorage.removeItem("user");
  //   localStorage.removeItem("roles");
  // }

  // getTasks(processInstance : string){

  //   return this.httpClient.get(HOST_URL+'/welcome/get/tasks/'.concat(processInstance)) as Observable<any>
  // }

  // claimTask(taskId){
  //   return this.httpClient.post(HOST_URL+'/welcome/tasks/claim/'.concat(taskId), null) as Observable<any>
  // }

  // completeTask(taskId){
  //   return this.httpClient.post(HOST_URL+'/welcome/tasks/complete/'.concat(taskId), null) as Observable<any>
  // }
}
