DO $$ DECLARE r RECORD;
BEGIN
  FOR r IN
    SELECT tc.constraint_name, tc.table_name
    FROM information_schema.table_constraints tc
    JOIN information_schema.referential_constraints rc
      ON tc.constraint_name = rc.constraint_name
    JOIN information_schema.constraint_column_usage ccu
      ON rc.unique_constraint_name = ccu.constraint_name
    WHERE ccu.table_name = 'departments' AND tc.constraint_type = 'FOREIGN KEY'
  LOOP
    EXECUTE 'ALTER TABLE ' || quote_ident(r.table_name)
            || ' DROP CONSTRAINT IF EXISTS ' || quote_ident(r.constraint_name);
  END LOOP;
END $$;
DROP TABLE IF EXISTS "departments";
DO $$ DECLARE r RECORD;
BEGIN
  FOR r IN
    SELECT tc.constraint_name, tc.table_name
    FROM information_schema.table_constraints tc
    JOIN information_schema.referential_constraints rc
      ON tc.constraint_name = rc.constraint_name
    JOIN information_schema.constraint_column_usage ccu
      ON rc.unique_constraint_name = ccu.constraint_name
    WHERE ccu.table_name = 'employee_documents' AND tc.constraint_type = 'FOREIGN KEY'
  LOOP
    EXECUTE 'ALTER TABLE ' || quote_ident(r.table_name)
            || ' DROP CONSTRAINT IF EXISTS ' || quote_ident(r.constraint_name);
  END LOOP;
END $$;
DROP TABLE IF EXISTS "employee_documents";
DO $$ DECLARE r RECORD;
BEGIN
  FOR r IN
    SELECT tc.constraint_name, tc.table_name
    FROM information_schema.table_constraints tc
    JOIN information_schema.referential_constraints rc
      ON tc.constraint_name = rc.constraint_name
    JOIN information_schema.constraint_column_usage ccu
      ON rc.unique_constraint_name = ccu.constraint_name
    WHERE ccu.table_name = 'time_off_requests' AND tc.constraint_type = 'FOREIGN KEY'
  LOOP
    EXECUTE 'ALTER TABLE ' || quote_ident(r.table_name)
            || ' DROP CONSTRAINT IF EXISTS ' || quote_ident(r.constraint_name);
  END LOOP;
END $$;
DROP TABLE IF EXISTS "time_off_requests";
DO $$ DECLARE r RECORD;
BEGIN
  FOR r IN
    SELECT tc.constraint_name, tc.table_name
    FROM information_schema.table_constraints tc
    JOIN information_schema.referential_constraints rc
      ON tc.constraint_name = rc.constraint_name
    JOIN information_schema.constraint_column_usage ccu
      ON rc.unique_constraint_name = ccu.constraint_name
    WHERE ccu.table_name = 'users' AND tc.constraint_type = 'FOREIGN KEY'
  LOOP
    EXECUTE 'ALTER TABLE ' || quote_ident(r.table_name)
            || ' DROP CONSTRAINT IF EXISTS ' || quote_ident(r.constraint_name);
  END LOOP;
END $$;
DROP TABLE IF EXISTS "users";
-- RENAME detected: was "employee_documents"
ALTER TABLE "employee_documents" RENAME TO "employees";
CREATE TABLE IF NOT EXISTS "employees" (
  "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  "phone" TEXT,
  "created_at" TIMESTAMPTZ NOT NULL DEFAULT NOW(),
  "updated_at" TIMESTAMPTZ NOT NULL DEFAULT NOW()
);