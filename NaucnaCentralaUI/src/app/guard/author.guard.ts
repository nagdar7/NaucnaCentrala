import { Injectable } from "@angular/core";
import { CanActivate } from "@angular/router";

@Injectable()
export class Author implements CanActivate {
  constructor() {}

  canActivate() {
    return (
      localStorage.getItem("roles") &&
      localStorage.getItem("roles").indexOf("AUTHOR") >= 0
    );
  }
}
