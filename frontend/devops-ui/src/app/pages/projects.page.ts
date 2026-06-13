import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

import {
  CreateProjectRequest,
  ProjectResponse,
  ProjectService
} from '../core/services/project.service';

@Component({
  selector: 'app-projects-page',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './projects.page.html',
  styleUrl: './projects.page.scss'
})
export class ProjectsPage implements OnInit {
  projects: ProjectResponse[] = [];

  isLoading = false;
  isCreating = false;
  errorMessage = '';
  successMessage = '';

  form: CreateProjectRequest = {
    name: 'DevOps Control Center',
    description: 'Main platform for managing pipelines, deployments and monitoring.',
    repository: {
      provider: 'GitHub',
      repositoryUrl: 'https://github.com/example/devops-control-center',
      defaultBranch: 'main',
      webhookEnabled: true
    },
    environments: [
      {
        name: 'Development',
        type: 'DEVELOPMENT',
        baseUrl: 'http://localhost:4200',
        protectedEnvironment: false
      },
      {
        name: 'Production',
        type: 'PRODUCTION',
        baseUrl: 'https://devops.example.com',
        protectedEnvironment: true
      }
    ]
  };

  constructor(private projectService: ProjectService) {}

  ngOnInit(): void {
    this.loadProjects();
  }

  loadProjects(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.projectService.getProjects().subscribe({
      next: (projects) => {
        this.projects = projects;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Could not load projects.';
        this.isLoading = false;
      }
    });
  }

  createProject(): void {
    this.isCreating = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.projectService.createProject(this.form).subscribe({
      next: () => {
        this.successMessage = 'Project created successfully.';
        this.isCreating = false;
        this.loadProjects();
      },
      error: () => {
        this.errorMessage = 'Could not create project.';
        this.isCreating = false;
      }
    });
  }

  deleteProject(project: ProjectResponse): void {
    if (!confirm(`Delete project "${project.name}"?`)) {
      return;
    }

    this.projectService.deleteProject(project.id).subscribe({
      next: () => this.loadProjects(),
      error: () => {
        this.errorMessage = 'Could not delete project.';
      }
    });
  }
}
