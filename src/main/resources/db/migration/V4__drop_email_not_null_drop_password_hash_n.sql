ALTER TABLE "users" ALTER COLUMN "email" DROP NOT NULL;
ALTER TABLE "users" ALTER COLUMN "password_hash" DROP NOT NULL;
ALTER TABLE "users" ALTER COLUMN "role" DROP NOT NULL;
CREATE TABLE IF NOT EXISTS employee_skills (
    id UUID NOT NULL PRIMARY KEY,
    employee_id UUID,
    skill_name TEXT,
    proficiency_level TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);