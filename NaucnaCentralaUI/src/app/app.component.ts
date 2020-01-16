import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  private user = JSON.parse(localStorage.getItem('user'));
  private role = localStorage.getItem('role');

  loggedIn(){
    if(this.user){
      return true; 
    }else{
      return false;
    }
  }

  notLoggedIn(){
    if(!this.user){
      return true; 
    }else{
      return false;
    }
  }

  isAdmin(){
    if(this.role == "ADMIN"){
      return true; 
    }else{
      return false;
    }
  }
}
