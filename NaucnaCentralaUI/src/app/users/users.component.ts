import { Component, OnInit } from '@angular/core';
import {UserService} from '../services/users/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  private users = [];
  private user = JSON.parse(localStorage.getItem('user'));
  private role = localStorage.getItem('role');

  constructor(private userService : UserService) {

    let x = this.userService.fetchUsers();
    x.subscribe(
      res => {
        console.log(res);
        this.users = res;
      },
      error => {
        console.log("Ne valja...")
      });
   }

  ngOnInit() {
  }

  isAdmin(temp_user){
    if(temp_user.role == "ADMIN"){
      return true; 
    }else{
      return false;
    }
  }

}
