# 0001. Devices are declared by the user, not detected

Date: 2026-09-27
Status: Accepted

## Context

SaveDat's core claim is that a file is exposed when its only copies sit on a
single device. That claim is only as good as the tool's idea of what counts
as a separate device.

Java's standard library can identify the volume a path sits on, but not the
physical disk that volume lives on. Two partitions on one disk appear as two
separate volumes to the operating system, while failing together when the
disk fails. A dual-boot machine is the everyday example: two volumes, one
disk, one failure.

Reporting those as independent copies would produce exactly the false
confidence the tool exists to remove.

## Decision

The user declares devices on the command line as labels:

    savedat laptop=C:/Projects laptop=D:/Archive usb=E:/

Locations sharing a label are one device. Content counts as having an
independent copy only when it is found under a different label.

## Alternatives considered

**Detect volumes automatically.** Rejected: volumes are not disks, so the
tool would silently over-report safety, which is worse than asking.

**Treat every location as independent.** Rejected: two folders on one disk
would be reported as a backup of each other.

**Ask the operating system for physical disk information.** Rejected for
v0.1: it requires platform-specific code on Windows and Linux, which
conflicts with the cross-platform requirement and the scope of this version.

## Consequences

- The tool trusts the user's labels. The README must state this plainly.
- A mislabelled device produces a wrong answer with no warning.
- The command line is more verbose than passing bare paths.
- Automatic detection can be added later without changing the model, since a
  detected device would simply be a label the tool filled in itself.