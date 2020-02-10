import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';

@Injectable()
export class Notauthorized implements CanActivate {

  constructor() { }

  canActivate() {
    if (!localStorage.getItem('jwt-token')) {
      return true;
    } else {
      return false;
    }
  }
}