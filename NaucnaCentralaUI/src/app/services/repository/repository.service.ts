import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RepositoryService {

  categories = [];
  languages = [];
  books = [];

  constructor(private httpClient: HttpClient, private http : Http) { 
    

  }


  startProcess(){
    return this.httpClient.get('http://localhost:8080/welcome/get') as Observable<any>
  }

  getTasks(processInstance : string){

    return this.httpClient.get('http://localhost:8080/welcome/get/tasks/'.concat(processInstance)) as Observable<any>
  }

  claimTask(taskId){
    return this.httpClient.post('http://localhost:8080/welcome/tasks/claim/'.concat(taskId), null) as Observable<any>
  }

  completeTask(taskId){
    return this.httpClient.post('http://localhost:8080/welcome/tasks/complete/'.concat(taskId), null) as Observable<any>
  }

}
