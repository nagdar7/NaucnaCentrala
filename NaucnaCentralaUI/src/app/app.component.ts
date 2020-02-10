import { Editor } from "./guard/editor.guard";
import { Router } from "@angular/router";
import { Reviewer } from "./guard/reviewer.guard";
import { Authorized } from "./guard/authorized.guard";
import { Component } from "@angular/core";
import { UserService } from "./services/user/user.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"]
})
export class AppComponent {
  title = "app";

  constructor(
    private userService: UserService,
    private authorized: Authorized,
    private reviewer: Reviewer,
    private editor: Editor,
    private router: Router
  ) {}

  public isReviewer() {
    return this.reviewer.canActivate();
  }
  public isEditor() {
    return this.editor.canActivate();
  }

  public loggedIn() {
    if (this.authorized.canActivate()) {
      return true;
    } else {
      return false;
    }
  }

  public logout() {
    this.userService.logout();
    this.router.navigate(["/"]);
  }
}
