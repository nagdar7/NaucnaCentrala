import { DatePipe } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { UserService } from "../services/user/user.service";
import { Router, ActivatedRoute } from "@angular/router";
import { TaskService } from "./../services/task/task.service";

// import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: "app-task",
  templateUrl: "./task.component.html",
  styleUrls: ["./task.component.css"]
})
export class TaskComponent implements OnInit {
  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];
  private createTaskState = true;
  taskId;

  // private user = {
  //   username: "",
  //   password: ""
  // }

  // private jwtHelper = new JwtHelperService();

  constructor(
    private taskService: TaskService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.taskId = this.route.snapshot.params["taskId"];
    let x = this.taskService.getTask(this.taskId);
    x.subscribe(
      res => {
        console.log(res);
        //this.categories = res;
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach(field => {
          if (field.type.name == "enum") {
            this.enumValues[field.id] = Object.keys(field.type.values);
          }
        });
      },
      err => {
        console.log("Error occured", err);
        alert(err.error.message);
      }
    );
  }

  ngOnInit() {}

  onSubmit(value, form) {
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      var vvv = value[property];
      if (!!value[property].join) {
        vvv = value[property].join(",");
      }
      var field = this.formFields.filter(x => x.id == property)[0];
      if (!this.isReadonly(field)) {
        if (field.type.name == "date") {
          console.log(vvv);
          var datePipe = new DatePipe("en-US");
          vvv = datePipe.transform(vvv, "dd/MM/yyyy");
        }
        o.push({ fieldId: property, fieldValue: vvv });
      }
    }

    console.log(o);

    let x = this.taskService.submitTask(this.formFieldsDto.taskId, o);

    x.subscribe(
      res => {
        console.log(res);

        alert("Task submittion successfull!");

        this.router.navigate(["/task"]);
      },
      err => {
        console.log("Error occured", err);
        alert(err.error.message);
      }
    );
  }

  isReadonly(field) {
    return field.validationConstraints.some(x => x.name === "readonly");
  }
}
