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
export class TaskService {
  taskId: string;

  constructor(private httpClient: HttpClient, private http: Http) {}

  findAll() {
    return this.httpClient.get(`${HOST_URL}/api/v1/task`) as Observable<any>;
  }

  getTask(taskId) {
    return this.httpClient.get(
      `${HOST_URL}/api/v1/task/${taskId}`
    ) as Observable<any>;
  }

  submitTask(taskId, form) {
    return this.httpClient.post(
      `${HOST_URL}/api/v1/task/${taskId}`,
      form
    ) as Observable<any>;
  }
}
