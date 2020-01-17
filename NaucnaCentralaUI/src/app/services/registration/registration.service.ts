import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

import { HOST_URL } from '../../../config'

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  categories = [];
  languages = [];
  books = [];

  constructor(private httpClient: HttpClient, private http: Http) {
  }

  startProcess() {
    return this.httpClient.get(HOST_URL + '/registration') as Observable<any>
  }

  registerUser(user, taskId) {
    return this.httpClient.post(HOST_URL + '/registration/'.concat(taskId), user) as Observable<any>;
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

}
