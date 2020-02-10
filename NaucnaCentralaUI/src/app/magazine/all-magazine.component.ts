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
  public selectMagazineForPaper = false;
  private processInstance;
  private taskId;

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

  public submitWorkToMagazine() {
    let x = this.magazineService.startProcess("publish-paper");
    x.subscribe(
      res => {
        console.log(res);
        //this.categories = res;
        // this.formFieldsDto = res;
        // this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.taskId = res.taskId;
        // this.formFields.forEach(field => {
        //   if (field.type.name == "enum") {
        //     this.enumValues[field.id] = Object.keys(field.type.values);
        //   }
        // });
        this.selectMagazineForPaper = true;
      },
      err => {
        console.log("Error occured", err);
        alert(err.error.message);
      }
    );
  }

  public publishPaper(magazineId) {
    console.log(magazineId);
    this.magazineService.taskId = this.taskId;
    this.selectMagazineForPaper = false;
    this.router.navigate(["/magazine/" + magazineId + "/submit-paper"]);
  }

  ngOnInit() {}
}
