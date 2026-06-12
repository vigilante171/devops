import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface RepositoryConfig {
  provider: string;
  repositoryUrl: string;
  defaultBranch: string;
  webhookEnabled: boolean;
}

export interface DevOpsEnvironment {
  name: string;
  type: 'DEVELOPMENT' | 'TESTING' | 'STAGING' | 'PRODUCTION';
  baseUrl: string;
  protectedEnvironment: boolean;
}

export interface CreateProjectRequest {
  name: string;
  description: string;
  repository: RepositoryConfig;
  environments: DevOpsEnvironment[];
}

export interface ProjectResponse {
  id: string;
  name: string;
  description: string;
  ownerEmail: string;
  status: string;
  repository: RepositoryConfig;
  environments: DevOpsEnvironment[];
  createdAt: string;
  updatedAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private readonly apiUrl = 'http://localhost:8080/api/projects';

  constructor(private http: HttpClient) {}

  getProjects(): Observable<ProjectResponse[]> {
    return this.http.get<ProjectResponse[]>(this.apiUrl);
  }

  createProject(request: CreateProjectRequest): Observable<ProjectResponse> {
    return this.http.post<ProjectResponse>(this.apiUrl, request);
  }

  deleteProject(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
