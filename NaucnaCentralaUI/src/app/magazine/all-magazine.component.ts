import { MagazineService } from "./../services/magazine/magazine.service";
import { Component, OnInit } from "@angular/core";
import { UserService } from "../services/user/user.service";
import { Router } from "@angular/router";
import { Author } from "./../guard/author.guard";
import { Reviewer } from "./../guard/reviewer.guard";

// import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: "app-all-magazine",
  templateUrl: "./all-magazine.component.html",
  styleUrls: ["./magazine.component.css"]
})
export class AllMagazineComponent implements OnInit {
  public allMagazines = [];

  constructor(
    private magazineService: MagazineService,
    private router: Router,
    private reviewer: Reviewer,
    private author: Author
  ) {
    let x = this.magazineService.findAll();
    x.subscribe(
      res => {
        console.log(res);
        //this.categories = res;
        this.allMagazines = res;
      },
      err => {
        console.log("Error occured", err);
        alert(err.error.message);
      }
    );
  }

  public isReviewer() {
    return this.reviewer.canActivate();
  }

  public isAuthor() {
    return this.author.canActivate();
  }

  public publishPaper(magazineId) {
    console.log(magazineId);
  }

  ngOnInit() {}
}
