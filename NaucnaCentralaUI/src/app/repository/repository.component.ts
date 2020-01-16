import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-repository',
  templateUrl: './repository.component.html',
  styleUrls: ['./repository.component.css']
})
export class RepositoryComponent implements OnInit {

  private user = JSON.parse(localStorage.getItem('user'));
  private role = localStorage.getItem('role');

  isAdmin(){
    if(this.role == "ADMIN"){
      return true; 
    }else{
      return false;
    }
  }
  constructor() { }

  ngOnInit() {
  }

}
