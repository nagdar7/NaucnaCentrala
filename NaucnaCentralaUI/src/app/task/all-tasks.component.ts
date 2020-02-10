import { TaskService } from "./../services/task/task.service";
import { Component, OnInit } from "@angular/core";
import { UserService } from "../services/user/user.service";
import { Router } from "@angular/router";
import { Author } from "./../guard/author.guard";
import { Reviewer } from "./../guard/reviewer.guard";

// import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: "app-all-tasks",
  templateUrl: "./all-tasks.component.html",
  styleUrls: ["./task.component.css"]
})
export class AllTasksComponent implements OnInit {
  public allTasks = [];
  public selectMagazineForPaper = false;
  private processInstance;
  private taskId;

  constructor(
    private taskService: TaskService,
    private router: Router,
    private reviewer: Reviewer,
    private author: Author
  ) {
    let x = this.taskService.findAll();
    x.subscribe(
      res => {
        console.log(res);
        //this.categories = res;
        this.allTasks = res;
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

  //   public submitWorkToMagazine() {
  //     let x = this.taskService.startProcess("publish-paper");
  //     x.subscribe(
  //       res => {
  //         console.log(res);
  //         //this.categories = res;
  //         // this.formFieldsDto = res;
  //         // this.formFields = res.formFields;
  //         this.processInstance = res.processInstanceId;
  //         this.taskId = res.taskId;
  //         // this.formFields.forEach(field => {
  //         //   if (field.type.name == "enum") {
  //         //     this.enumValues[field.id] = Object.keys(field.type.values);
  //         //   }
  //         // });
  //         this.selectMagazineForPaper = true;
  //       },
  //       err => {
  //         console.log("Error occured", err);
  //         alert(err.error.message);
  //       }
  //     );
  //   }

  public startTask(taskId) {
    // console.log(taskId);
    // this.taskService.taskId = this.taskId;
    // this.selectMagazineForPaper = false;
    this.router.navigate(["/task/" + taskId]);
  }

  ngOnInit() {}
}
